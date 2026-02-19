package controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.time.format.DateTimeFormatter;

import org.bson.Document;

import com.mongodb.client.model.Filters;

import config.MongoDbUtil;
import entities.Aeropuertos;
import repositories.AeropuertosDAO;
import utils.LanguageUtils;
import utils.TablePrinter;
import utils.ValidationUtils;
import utils.WeatherValidationUtil;

/**
 * Weather (clima) module. MongoDB for datos_clima; MySQL AeropuertosDAO for airport list.
 */
public class WeatherController {

	private static final int VIENTO_UMBRAL_KMH = 50;

	public void insertarNuevoClima() {
		try {
			AeropuertosDAO.readAll(); // TablePrinter shows available airports
			Integer airportId = ValidationUtils.readInteger(LanguageUtils.get("prompt.weather.airport.id"));
			Aeropuertos a = AeropuertosDAO.getById(airportId);
			if (a == null) {
				System.out.println(LanguageUtils.get("error.weather.airport.notfound"));
				return;
			}

			String fecha = WeatherValidationUtil.readFechaClima(LanguageUtils.get("prompt.weather.fecha")).toString();
			String hora = ValidationUtils.readLocalTime(LanguageUtils.get("prompt.weather.hora"))
					.format(DateTimeFormatter.ofPattern("HH:mm"));
			double temperatura = WeatherValidationUtil.readTemperatura(LanguageUtils.get("prompt.weather.temperatura"));
			int humedad = WeatherValidationUtil.readHumedad(LanguageUtils.get("prompt.weather.humedad"));
			double presionAire = WeatherValidationUtil.readPresionAire(LanguageUtils.get("prompt.weather.presion"));
			int viento = WeatherValidationUtil.readViento(LanguageUtils.get("prompt.weather.viento"));
			int direccionViento = WeatherValidationUtil.readDireccionViento(
					LanguageUtils.get("prompt.weather.direccion.viento"));
			String clima = WeatherValidationUtil.readClima(LanguageUtils.get("prompt.weather.clima"));

			// store airport data in Mongo (with geolocation)
			Document aeropuerto = new Document()
					.append("aeropuerto_id", a.getAeropuertoId())
					.append("iata", a.getIata())
					.append("icao", a.getIcao())
					.append("nombre", a.getNombre())
					.append("ciudad", a.getCiudad())
					.append("pais", a.getPais())
					.append("latitud", a.getLatitud())
					.append("longitud", a.getLongitud());

			Document doc = new Document()
					.append("fecha", fecha)
					.append("hora", hora)
					.append("aeropuerto", aeropuerto)
					.append("temperatura", temperatura)
					.append("humedad", humedad)
					.append("presion_aire", presionAire)
					.append("viento", viento)
					.append("clima", clima)
					.append("direccion_viento", direccionViento);

			MongoDbUtil.getClimaCollection().insertOne(doc);
			System.out.println(LanguageUtils.get("info.weather.insert.ok"));

			if ("Tormenta".equals(clima)) {
				showAviso(LanguageUtils.get("aviso.weather.tormenta"));
			} else if (viento > VIENTO_UMBRAL_KMH) {
				showAviso(LanguageUtils.get("aviso.weather.viento"));
			}
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.weather.insert.fail") + " " + e.getMessage());
		}
	}

	// aviso in console so it never blocks (no modal dialog)
	private void showAviso(String message) {
		System.out.println("\n" + LanguageUtils.get("aviso.weather.prefix") + " " + LanguageUtils.get("aviso.weather.title") + ": " + message);
	}

	public void consultarPorAeropuerto() {
		try {
			AeropuertosDAO.readAll();
			Integer airportId = ValidationUtils.readInteger(LanguageUtils.get("prompt.weather.airport.id"));
			Aeropuertos a = AeropuertosDAO.getById(airportId);
			if (a == null) {
				System.out.println(LanguageUtils.get("error.weather.airport.notfound"));
				return;
			}
			List<Document> list = MongoDbUtil.getClimaCollection()
					.find(Filters.eq("aeropuerto.aeropuerto_id", airportId))
					.into(new ArrayList<>());
			printWeatherTable(list);
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.general") + e.getMessage());
		}
	}

	public void consultarPorRangoFecha() {
		try {
			String fechaInicio = ValidationUtils.readLocalDate(LanguageUtils.get("prompt.weather.fecha.inicio")).toString();
			String fechaFin = ValidationUtils.readLocalDate(LanguageUtils.get("prompt.weather.fecha.fin")).toString();
			List<Document> list = MongoDbUtil.getClimaCollection()
					.find(Filters.and(
							Filters.gte("fecha", fechaInicio),
							Filters.lte("fecha", fechaFin)))
					.into(new ArrayList<>());
			printWeatherTable(list);
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.general") + e.getMessage());
		}
	}

	public void consultarPorNieblaOTormenta() {
		try {
			List<Document> list = MongoDbUtil.getClimaCollection()
					.find(Filters.in("clima", Arrays.asList("Niebla", "Tormenta")))
					.into(new ArrayList<>());
			printWeatherTable(list);
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.general") + e.getMessage());
		}
	}

	private void printWeatherTable(List<Document> list) {
		if (list == null || list.isEmpty()) {
			System.out.println(LanguageUtils.get("info.weather.no.results"));
			return;
		}
		TablePrinter tp = new TablePrinter().headers(
				LanguageUtils.get("table.weather.col.fecha"),
				LanguageUtils.get("table.weather.col.hora"),
				LanguageUtils.get("table.weather.col.iata"),
				LanguageUtils.get("table.weather.col.aeropuerto"),
				LanguageUtils.get("table.weather.col.ciudad"),
				LanguageUtils.get("table.weather.col.temp"),
				LanguageUtils.get("table.weather.col.hum"),
				LanguageUtils.get("table.weather.col.pres"),
				LanguageUtils.get("table.weather.col.viento"),
				LanguageUtils.get("table.weather.col.clima"),
				LanguageUtils.get("table.weather.col.dir"));
		for (Document doc : list) {
			Document aeropuerto = doc.get("aeropuerto", Document.class);
			String iata = aeropuerto != null ? nullToEmpty(aeropuerto.getString("iata")) : "";
			String nombre = aeropuerto != null ? nullToEmpty(aeropuerto.getString("nombre")) : "";
			String ciudad = aeropuerto != null ? nullToEmpty(aeropuerto.getString("ciudad")) : "";
			tp.row(
					nullToEmpty(doc.getString("fecha")),
					nullToEmpty(doc.getString("hora")),
					iata,
					nombre,
					ciudad,
					doc.get("temperatura") != null ? String.valueOf(doc.get("temperatura")) : "",
					doc.get("humedad") != null ? String.valueOf(doc.get("humedad")) : "",
					doc.get("presion_aire") != null ? String.valueOf(doc.get("presion_aire")) : "",
					doc.get("viento") != null ? String.valueOf(doc.get("viento")) : "",
					nullToEmpty(doc.getString("clima")),
					doc.get("direccion_viento") != null ? String.valueOf(doc.get("direccion_viento")) : "");
		}
		tp.print();
	}

	private static String nullToEmpty(String s) {
		return s == null ? "" : s;
	}
}

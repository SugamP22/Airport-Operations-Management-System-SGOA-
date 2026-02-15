package utils;

import java.util.List;

import entities.HorarioVuelo;
import entities.Pasajero;
import entities.Reserva;
import repositories.HorarioVueloDAO;
import repositories.PasajeroDAO;

public class ReservaValidationUtil {
	public static HorarioVuelo readHorario() {
		List<HorarioVuelo> listaHorario = HorarioVueloDAO.getAllSchedules();
		if (listaHorario.isEmpty()) {
			System.out.println(LanguageUtils.get("error.reserva.emptySchedules"));
			return null;
		}
		for (HorarioVuelo horarioVuelo : listaHorario) {
			System.out.println(horarioVuelo.toString());
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
		}
		while (true) {
			BoxedMessageUtils.horizontalRow("*");
			String idHorario = ValidationUtils.readString(LanguageUtils.get("reservation.select.schedule"));
			System.out.println();
			HorarioVuelo h = HorarioVueloDAO.getSchedulesbyFlight(idHorario);
			if (h != null) {
				return h;
			}
			System.out.println(LanguageUtils.get("error.reserva.invalidSchedule"));
		}
	}

	public static Pasajero readPasajero() {
		List<Pasajero> listaPasajero = PasajeroDAO.getAllPasajeros();
		if (listaPasajero.isEmpty()) {
			System.out.println(LanguageUtils.get("error.reserva.emptyPassengers"));
			return null;
		}
		for (Pasajero pasajero : listaPasajero) {
			System.out.println(String.format(LanguageUtils.get("reservation.passenger.preview"), pasajero.getPasajeroId(),
					pasajero.getNombre(), pasajero.getApellido(), pasajero.getPassport()));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
		}
		while (true) {
			BoxedMessageUtils.horizontalRow("*");
			Integer idPasajero = ValidationUtils.readInteger(LanguageUtils.get("reservation.select.passenger"));
			System.out.println();
			Pasajero p = PasajeroDAO.getPasajeroById(idPasajero);
			if (p != null) {
				return p;
			}
			System.out.println(LanguageUtils.get("error.reserva.invalidPassenger"));
		}
	}

	public static Reserva readData(HorarioVuelo h, Pasajero p) {
		int idVuelo;
		do {
			idVuelo = ValidationUtils.readInt(LanguageUtils.get("reservation.input.flightId"));
			if (idVuelo <= 0) {
				System.out.println(LanguageUtils.get("error.reserva.invalidFlightId"));
			}
		} while (idVuelo <= 0);
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();

		String asiento = ValidationUtils.readString(LanguageUtils.get("reservation.input.seat"));
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();

		double precio = ValidationUtils.readDouble(LanguageUtils.get("reservation.input.price"));
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();
		Reserva reserva = new Reserva();
		reserva.setHorarioVuelo(h);
		reserva.setPasajero(p);
		reserva.setVueloId(idVuelo);
		reserva.setAsiento(asiento);
		reserva.setPrecio(precio);
		return reserva;
	}
}

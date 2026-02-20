package controllers;

/**
 * Controller  used to manage passenger data (create passengers and list all
 * passengers) in the console UI.
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.DetallePasajero;
import entities.Pasajero;
import entities.Reserva;
import repositories.PasajeroDAO;
import utils.LanguageUtils;
import utils.PasajeroValidationUtil;
import utils.TablePrinter;
import utils.ValidationUtils;

public class PassengersController {

	public void createPassenger() {
		// Here I read all the passenger data from console, encrypt sensitive fields and
		// save passenger + details
		try {
			String passport = PasajeroValidationUtil.readPassport(LanguageUtils.get("passenger.input.passport"));
			String nombre = ValidationUtils.readString(LanguageUtils.get("passenger.input.nombre"));
			String appelido = ValidationUtils.readString(LanguageUtils.get("passenger.input.apellido"));
			List<Reserva> lista = new ArrayList<>();
			DetallePasajero d = new DetallePasajero();
			Pasajero p = new Pasajero(passport, nombre, appelido, lista, d);
			LocalDate fechaNacimineto = ValidationUtils
					.readLocalDate(LanguageUtils.get("passenger.input.fechaNacimiento"));
			d.setFecha_nacimiento(fechaNacimineto);
			char sexo = ValidationUtils.readSexo(LanguageUtils.get("passenger.input.sexo"));
			d.setSexo(sexo);
			String calle = ValidationUtils.readString(LanguageUtils.get("passenger.input.calle"));
			d.setCalle(calle);
			String ciudad = ValidationUtils.readString(LanguageUtils.get("passenger.input.ciudad"));
			d.setCiudad(ciudad);
			int codigoPostal = PasajeroValidationUtil
					.readCodigoPostal(LanguageUtils.get("passenger.input.codigoPostal"));
			d.setCiudad_postal(codigoPostal);
			String pais = ValidationUtils.readString(LanguageUtils.get("passenger.input.pais"));
			d.setPais(pais);
			String email = PasajeroValidationUtil.readEmail(LanguageUtils.get("passenger.input.email"));
			d.setEmail(email);
			String telefono = PasajeroValidationUtil.readTelefono(LanguageUtils.get("passenger.input.telefono"));
			d.setTelefono(telefono);
			d.setPasajero(p);
			p.setDetallesPasajeros(d);
			PasajeroDAO.createPasajero(p);
			System.out.println(LanguageUtils.get("passenger.create.success"));
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("passenger.create.error"));
		}
	}

	public void showAllPassengers() {
		// Simple helper I use to print all passengers as a small table, without
		// decrypting anything
		try {
			List<Pasajero> pasajeros = PasajeroDAO.getAllPasajeros();
			if (pasajeros == null || pasajeros.isEmpty()) {
				System.out.println(LanguageUtils.get("error.reserva.emptyPassengers"));
				return;
			}
			TablePrinter tp = new TablePrinter().headers("ID", "Name", "Surname", "Passport");
			for (Pasajero p : pasajeros) {
				String fullName = p.getNombre() != null ? p.getNombre() : "";
				String surname = p.getApellido() != null ? p.getApellido() : "";
				String passport = p.getPassport() != null ? p.getPassport() : "";
				tp.row(p.getPasajeroId() != null ? p.getPasajeroId().toString() : "", fullName, surname, passport);
			}
			tp.print();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}

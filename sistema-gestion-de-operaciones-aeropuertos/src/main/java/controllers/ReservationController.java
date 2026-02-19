package controllers;

/**
 * Controller I use to work with reservations: create, search, list and verify
 * DSA signatures from the console.
 */

import java.util.List;

import entities.HorarioVuelo;
import entities.Pasajero;
import entities.Reserva;
import repositories.PasajeroDAO;
import repositories.ReservaDAO;
import utils.BoxedMessageUtils;
import utils.DesUtil;
import utils.LanguageUtils;
import utils.ReservaValidationUtil;
import utils.SignatureUtil;
import utils.TablePrinter;
import utils.ValidationUtils;

public class ReservationController {
	boolean empty;

	public void showAll() {
		// I use this to list all reservations before searching or verifying signatures
		empty = true;
		BoxedMessageUtils.horizontalRow("-");
		System.out.println();
		try {
			List<Reserva> list = ReservaDAO.getAllReserva();
			TablePrinter tp = new TablePrinter().headers("ID", "FlightNo", "Seat", "Passenger", "Price");
			for (Reserva reserva : list) {
				empty = false;
				String flightNo = reserva.getHorarioVuelo() != null ? reserva.getHorarioVuelo().getNumeroVuelo()
						: String.valueOf(reserva.getVueloId());
				String seat = reserva.getAsiento() != null ? reserva.getAsiento() : "";
				String passengerName = reserva.getPasajero() != null ? reserva.getPasajero().getNombre() : "";
				String price = String.format("%.2f", reserva.getPrecio());
				tp.row(
						reserva.getReservaId() != null ? reserva.getReservaId().toString() : "",
						flightNo,
						seat,
						passengerName,
						price);
			}
			tp.print();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void searchReservation() {
		showAll();
		if (!empty) {
			BoxedMessageUtils.horizontalRow("*");
			Integer id = ValidationUtils.readInteger(LanguageUtils.get("reservation.search.id"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			try {
				Reserva r = ReservaDAO.getReservaByID(id);
				System.out.println(r.toString());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	public void createReservation() {
		// Main flow to create a reservation: choose schedule, passenger and data, then sign it
		try {
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			HorarioVuelo h = ReservaValidationUtil.readHorario();

			if (h == null) {
				return;
			}

			Pasajero p = ReservaValidationUtil.readPasajero();
			if (p == null) {
				return;
			}

			Reserva reserva = ReservaValidationUtil.readData(h, p);
			if (reserva != null) {
				ReservaDAO.createReserva(reserva);
				try {
					SignatureUtil.signReserva(reserva);
				} catch (Exception signEx) {
					System.out.println(signEx.getMessage());
				}
				System.out.println(LanguageUtils.get("reservation.create.success"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/** Cálculo del precio total por pasajero: listar pasajeros, elegir uno, mostrar total de sus reservas. */
	public void calcularPrecioTotalReservas() {
		try {
			BoxedMessageUtils.boxWithEvenSpacing(LanguageUtils.get("reservation.total.title"), "=");
			System.out.println();
			List<Pasajero> pasajeros = PasajeroDAO.getAllPasajeros();
			if (pasajeros == null || pasajeros.isEmpty()) {
				System.out.println(LanguageUtils.get("error.reserva.emptyPassengers"));
				return;
			}
			TablePrinter tp = new TablePrinter().headers("ID", "Name", "Surname", "Passport");
			for (Pasajero p : pasajeros) {
				String passport = p.getPassport() != null ? p.getPassport() : "";
				try {
					passport = DesUtil.decrypt(passport);
				} catch (Exception e) {
					// keep stored value if decrypt fails
				}
				tp.row(
						p.getPasajeroId() != null ? p.getPasajeroId().toString() : "",
						p.getNombre() != null ? p.getNombre() : "",
						p.getApellido() != null ? p.getApellido() : "",
						passport);
			}
			tp.print();
			BoxedMessageUtils.horizontalRow("*");
			Integer idPasajero = ValidationUtils.readInteger(LanguageUtils.get("reservation.select.passenger"));
			System.out.println();
			Pasajero pasajero = PasajeroDAO.getPasajeroById(idPasajero);
			if (pasajero == null) {
				System.out.println(LanguageUtils.get("error.reserva.invalidPassenger"));
				return;
			}
			List<Reserva> reservas = ReservaDAO.getReservasByPasajeroId(idPasajero);
			if (reservas.isEmpty()) {
				System.out.println(LanguageUtils.get("reservation.total.noReservasYet"));
				return;
			}
			double total = reservas.stream().mapToDouble(Reserva::getPrecio).sum();
			System.out.println(LanguageUtils.get("reservation.total.byPassenger") + " " + String.format("%.2f", total));
		} catch (Exception e) {
			System.out.println(LanguageUtils.get("error.general") + " " + e.getMessage());
		}
	}

	public void verifyReservation() {
		// I use this method when admin/employee wants to verify the DSA signature of one reservation
		showAll();
		if (!empty) {
			BoxedMessageUtils.horizontalRow("*");
			Integer id = ValidationUtils.readInteger(LanguageUtils.get("reservation.search.id"));
			BoxedMessageUtils.horizontalRow("-");
			System.out.println();
			try {
				Reserva r = ReservaDAO.getReservaByID(id);
				if (r == null) {
					System.out.println(LanguageUtils.get("error.reserva.notFound"));
					return;
				}
				SignatureUtil.verifyReserva(r);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}

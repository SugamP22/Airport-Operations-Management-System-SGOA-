package controllers;

/**
 * Controller I use to work with reservations: create, search, list and verify
 * DSA signatures from the console.
 */

import java.util.List;

import entities.HorarioVuelo;
import entities.Pasajero;
import entities.Reserva;
import repositories.ReservaDAO;
import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.ReservaValidationUtil;
import utils.SignatureUtil;
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
			for (Reserva reserva : list) {
				empty = false;
				System.out.println(reserva.toString());
				BoxedMessageUtils.horizontalRow("-");
				System.out.println();
			}
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

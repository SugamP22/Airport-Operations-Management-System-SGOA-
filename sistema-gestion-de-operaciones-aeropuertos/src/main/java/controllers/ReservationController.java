package controllers;

import java.util.List;

import entities.*;
import repositories.ReservaDAO;
import utils.BoxedMessageUtils;
import utils.LanguageUtils;
import utils.ReservaValidationUtil;
import utils.ValidationUtils;

public class ReservationController {
	boolean empty;

	public void showAll() {
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
				System.out.println(LanguageUtils.get("reservation.create.success"));
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}

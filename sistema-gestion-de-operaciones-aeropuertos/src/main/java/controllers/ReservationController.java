package controllers;

import java.util.List;

import entities.Reserva;
import repositories.ReservaDAO;
import utils.BoxedMessageUtils;
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
			Integer id = ValidationUtils.readInteger("Enter reservation id: ");
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

	}

}

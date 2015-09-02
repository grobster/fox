package controllers;

import models.Printer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.details;
import views.html.list;

import java.util.List;

/**
 * Created by quarles on 9/1/2015.
 */
public class Printers extends Controller {
    private static final Form<Printer> printerForm = Form.form(Printer.class);

    public static Result list() {
        List<Printer> printers = Printer.findAll();
        return ok(list.render(printers));
    }

    public static Result newPrinter() {

        return ok(details.render(printerForm));
    }

    public static Result details(String serialNumber) {
        final Printer printer = Printer.findBySerialNumber(serialNumber);
        Form<Printer> filledForm = printerForm.fill(printer);
        return ok(details.render(filledForm));
    }

    public static Result save() {
        Form<Printer> boundForm = printerForm.bindFromRequest();
        if (boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(details.render(boundForm));
        }

        Printer printer = boundForm.get();
        printer.save();
        flash("success", String.format("Successfully added printer %s", printer));

        return redirect(controllers.routes.Printers.sortLocation());
    }

    public static Result delete(String serial) {
        final Printer printer = Printer.findBySerialNumber(serial);
        if (printer == null) {
            return notFound(String.format("Printer %s does not exists.", serial));
        }
        Printer.remove(printer);
        return redirect(controllers.routes.Printers.list());
    }

    public static Result sortModel() {
        List<Printer> modelPrinters = Printer.sortByModel();
        return redirect(controllers.routes.Printers.list());
    }

    public static Result sortLocation() {
        List<Printer> locationPrinters = Printer.sortByLocation();
        return redirect(controllers.routes.Printers.list());
    }

    public static Result sortSerial() {
        List<Printer> serialPrinters = Printer.sortBySerial();
        return redirect(controllers.routes.Printers.list());
    }

    public static Result sortManufacturer() {
        List<Printer> manufacturerPrinters = Printer.sortByManufacturer();
        return redirect(controllers.routes.Printers.list());
    }

    public static Result sortIp() {
        List<Printer> ipPrinters = Printer.sortByIp();
        return redirect(controllers.routes.Printers.list());
    }
}

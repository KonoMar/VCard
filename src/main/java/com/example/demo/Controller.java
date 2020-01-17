package com.example.demo;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.VCardVersion;
import ezvcard.property.Address;
import ezvcard.property.Organization;
import ezvcard.property.StructuredName;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.*;

import javax.activation.MimeType;

@RestController
public class Controller {
    @GetMapping(value = "/users/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public String getICSFileHolidays(@PathVariable String name) throws IOException {
        StringBuffer buffer = new StringBuffer();
        String script = Utils.utilsScript();
        String style = Utils.utilsStyle();
        buffer.append(script);
        buffer.append(style);

        Document doc = Jsoup.connect("https://adm.edu.p.lodz.pl/user/users.php?search=" + name).get();
        Elements employees = doc.select("div.user-info");

        for (Element element : employees) {
            Elements e = element.select("a");
            Elements e2 = element.parent().getElementsByClass("item-content");

            String s = e.get(0).attributes().get("title");
            element.append("<a class=\"fullprofile-link btn btn-default\" href='vcard/" + s +
                    "'>vCard</a>");
            buffer.append(element.toString());
        }

        if (employees.isEmpty()) {
            buffer.append("<div class=\"alert alert-warning\">\n" +
                    "  <strong>Uwaga!</strong> Brak pracowników do wyswietlenia.\n" +
                    "</div>");
        }

        return buffer.toString();
    }

    @GetMapping("")
    public String getInfo() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<!-- Latest compiled and minified CSS -->\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">\n" +
                "\n" +
                "<!-- jQuery library -->\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\n" +
                "\n" +
                "<!-- Latest compiled JavaScript -->\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>\n" +
                "<body>\n" +
                "<center>\n" +
                "<h2>Generator vCard</h2>\n" +
                "<p>Wpisz pracownika by wygenerować vCard:</p>\n" +
                "\n" +
                "<button class=\"btn btn-default\" onclick=\"myFunction()\">Wyszukaj</button>\n" +
                "<input id=\"ab\" class=\"ab btn btn-default\">\n" +
                "</center>\n" +
                "<script>\n" +
                "function myFunction() {\n" +
                "var a = document.getElementById('ab').value;\n" +
                "location.replace('/users/'+a)\n" +
                "\n" +
                "}\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html> ";
    }

    @GetMapping(value = "/users/vcard/{name22}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<byte[]> getICSFileHoliday2s(@PathVariable String name22) throws IOException {
        VCard vcard = new VCard();
        StructuredName n = new StructuredName();
        n.setFamily(name22.split(" ")[0]);
        n.setGiven(name22.split(" ")[1]);


        Document doc = Jsoup.connect("https://adm.edu.p.lodz.pl/user/users.php?search=" + name22).get();
        Elements pracownicy = doc.select("div.user-info");
        Elements e2 = pracownicy.get(0).getElementsByClass("item-content");


        n.setLanguage("pl");
        n.getPrefixes().add("Mr");
        vcard.setStructuredName(n);
        Organization organization = new Organization();
        organization.setParameter("Afiliacja do",e2.get(0).html());
        vcard.addOrganization(organization);
        vcard.setFormattedName(name22);
        String str = Ezvcard.write(vcard).version(VCardVersion.V4_0).go();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + name22.replace(" ", "_") + ".vcf");
        responseHeaders.add("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);


        return new ResponseEntity<>(str.getBytes(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/user/profile.php")
    @ResponseStatus(code = HttpStatus.OK)
    public String redirect(@RequestParam String id) throws IOException {
        return Jsoup.connect("https://adm.edu.p.lodz.pl/user/profile.php?id=" + id).get().html();
    }

    @GetMapping(value = "/user/usermailform.php")
    @ResponseStatus(code = HttpStatus.OK)
    public String redirect2(@RequestParam String user) throws IOException {
        return Jsoup.connect("https://adm.edu.p.lodz.pl/user/usermailform.php?user=" + user).get().html();
    }
}

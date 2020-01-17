package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;

public class Utils {
    public static String utilsStyle() {
        return "<style>\n" +
                "\n" +
                "body{\n" +
                "  width: 100%;\n" +
                "}\n" +
                "\n" +
                ".user-info{\n" +
                "\n" +
                "  min-width: 600px;\n" +
                "  width: min-content;\n" +
                "  display: block;\n" +
                "  margin: 20px auto;\n" +
                "  color: white;\n" +
                "  padding: 20px;\n" +
                "  border: solid black;\n" +
                "  border-radius: 20px;\n" +
                "  background: rgb(2,0,36);\n" +
                "  background: green;\n" +
                "}\n" +
                "\n" +
                "a{\n" +
                "  margin: 1%;\n" +
                "  color: black;\n" +
                "}</style>";
    }

    public static String utilsScript() {
        return "<!-- Latest compiled and minified CSS -->\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">\n" +
                "\n" +
                "<!-- jQuery library -->\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>\n" +
                "\n" +
                "<!-- Latest compiled JavaScript -->\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min" +
                ".js\"></script>\n";
    }
}

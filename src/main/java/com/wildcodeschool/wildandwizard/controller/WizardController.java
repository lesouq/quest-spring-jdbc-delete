package com.wildcodeschool.wildandwizard.controller;

import com.wildcodeschool.wildandwizard.repository.WizardRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WizardController {

    private static final String DB_URL = null;
	private static final String DB_PASSWORD = null;
	private static final String DB_USER = null;
	private WizardRepository repository = new WizardRepository();

    @GetMapping("/wizard/delete")
    public String deleteWizard(@RequestParam Long id) {

        repository.deleteById(id);

        return "redirect:/wizards";
    }

    @GetMapping("/wizards")
    public String getAll(Model model) {

        model.addAttribute("wizards", repository.findAll());

        return "wizard_get_all";
    }
    
    public void deleteById(Long id) {
        try {
            Connection connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM wizard WHERE id=?"
            );
            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

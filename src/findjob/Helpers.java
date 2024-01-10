/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author BusraGural
 */
public class Helpers {
    static void disableTextFields(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(false);
            } else if (component instanceof Container) {
                // Recursive call for nested containers
                disableTextFields((Container) component);
            }
        }
    }
    static void enableTextFields(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(true);
            } else if (component instanceof Container) {
                enableTextFields((Container) component);
            }
        }
    }
    
    static void disableTableFields(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            for (int col = 0; col < table.getColumnCount(); col++) {
                table.getCellEditor(row, col).cancelCellEditing();
                Component component = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                if (component instanceof JTextField) {
                    ((JTextField) component).setEnabled(false);
                }
            }
        }
    }
    
    static void enableTableFields(JTable table) {
    for (int row = 0; row < table.getRowCount(); row++) {
        for (int col = 0; col < table.getColumnCount(); col++) {
            table.getCellEditor(row, col).cancelCellEditing();
            Component component = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
            if (component instanceof JTextField) {
                ((JTextField) component).setEnabled(true);
            }
        }
    }
}
    
    
    
    static LocalDate parseDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
     
}

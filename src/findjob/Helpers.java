/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;

import javax.swing.*;
import java.awt.*;

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
    
    
    
}

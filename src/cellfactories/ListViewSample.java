/*
 * Copyright (c)2012, 2014  Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package cellfactories;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ListViewSample extends Application {

    // Declaramos una ListView para poder visualizar graficamente la lista
    ListView<String> list = new ListView<>();
    // Decalramos una lista observable para poder controlar los cambios que e produzcan
    // y gestionarlos. Nos interesan los cambios en el foco (la seleccion)
    ObservableList<String> data = FXCollections.observableArrayList(
            "chocolate", "salmon", "gold", "coral", "darkorchid",
            "darkgoldenrod", "lightsalmon", "black", "rosybrown", "blue",
            "blueviolet", "brown");
    // Declaramos etiqueta que nos dirá el color del circulo seleccionado
    final Label label = new Label();

    @Override
    public void start(Stage stage) {
        // Creamos un VBox que sera el nodo raiz de la escena
        VBox box = new VBox();
        // Creamos la escena con unas dimensiones y con el VBox como nodo raiz
        Scene scene = new Scene(box, 200, 200);
        // Establecemos dicha escena como la escena activa del escenario
        stage.setScene(scene);
        // Damos titulo a la ventana
        stage.setTitle("ListViewSample");
        // Añadimos la lista y la etiqueta al VBox
        box.getChildren().addAll(list, label);
        // Establecemos que la lista se redimensione dependiendo de la cantidad de objetos
        // que haya en la lista
        VBox.setVgrow(list, Priority.ALWAYS);

        // Establecemos la posicion de la etiqueta que muestra el nombre del color y le asignamos una fuente
        label.setLayoutX(10);
        label.setLayoutY(115);
        label.setFont(Font.font("Verdana", 20));

        // Cargamos los elementos en la lista desde la lista observable
        list.setItems(data);

        // Cambiamos la clase a la que llama para corregirla
        // Ahora llama a un CellFactory que establece circulos de colores en lugar e rectangulos
        list.setCellFactory((ListView<String> l) -> new ColorCircleCell());
 
        // Creamos un listener para el valor seleccionado de la lista
        // y mostramos el nombre de su color
        list.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String old_val, String new_val) -> {
                    label.setText(new_val);
                    label.setTextFill(Color.web(new_val));
        });
        
        // Mostramos el escenario
        stage.show();
    }
    
    // Cambiamos la clase para crear circulos
    static class ColorCircleCell extends ListCell<String> {
        
        @Override
        public void updateItem(String item, boolean empty) {
            // La clase de la que extiende actualiza el elemento
            super.updateItem(item, empty);
            // Creamos un circulo
            Circle circ = new Circle();
            // Establecemos radio, ya que sin radio el circulo no se ve
            circ.setRadius(20);
            
            if (item != null) {
                circ.setFill(Color.web(item));
                setGraphic(circ);
            } else {
                setGraphic(null);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

package com.stddata.fx.uitest;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *  This file is part of SDSJavaFXControls.
 *
 *  SDSJavaFXControls is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  SDSJavaFXControls is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with SDSJavaFXControls.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2012 Standard Data Systems Limited
 * @author nigelm
 */
public class FXUiControlDemo extends Application
{
    public static void main(String[] args)
    {

        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)
    {

        UiControlDemoStage cad = new UiControlDemoStage (primaryStage);
        cad.show();
    }
}



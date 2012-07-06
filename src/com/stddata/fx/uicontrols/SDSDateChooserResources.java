package com.stddata.fx.uicontrols;

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

import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SDSDateChooserResources
{
  private static byte [] PRIOR_STEP = {
   71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -121, 0, 0, 115, 113, 113, 107, 105, 105, -120, -122, -122, -24, -24, -24, 126, 124, 124, 127, 125,
   125, 114, 113, 113, 117, 115, 115, 102, 99, 99, 96, 95, 95, 111, 110, 110, 66, 65, 65, 98, 97, 97, 120, 118, 118, -70, -71, -71, 73, 70,
   70, -99, -100, -100, 97, 96, 96, -109, -111, -111, 123, 122, 122, -74, -74, -74, 97, 94, 94, 96, 94, 94, 126, 123, 123, -123, -125, -125, -66, -68,
   -67, 120, 118, 119, -20, -22, -22, -74, -75, -75, 83, 81, 81, 106, 104, 104, 94, 90, 90, 92, 91, 91, 117, 118, 118, 108, 106, 106, -68, -68,
   -68, -123, -123, -122, 90, 88, 88, 84, 79, 80, -119, -121, -121, 100, 98, 98, -113, -115, -115, -50, -50, -50, 102, 101, 101, -96, -96, -96, -11, -11,
   -11, -93, -94, -94, -120, -121, -121, 95, 93, 93, -26, -26, -26, -84, -87, -87, 111, 111, 111, 87, 84, 84, -110, -110, -110, 114, 111, 111, 127, 127,
   126, -105, -107, -107, -51, -55, -53, -8, -8, -8, 108, 103, 105, -73, -74, -73, 125, 123, 123, -80, -81, -81, 83, 80, 80, 83, 83, 83, -100, -100,
   -100, 125, 125, 125, 84, 79, 79, -117, -119, -119, -108, -111, -111, -26, -25, -25, -101, -103, -103, 54, 54, 55, -119, -120, -120, 92, 89, 89, 78, 75,
   75, 85, 83, 83, 109, 106, 106, -127, -128, -128, -36, -38, -38, 116, 115, 115, 116, 113, 113, -81, -83, -83, 114, 111, 113, 123, 123, 123, -5, -5,
   -5, 90, 87, 86, 105, 103, 103, 67, 66, 66, 103, 101, 101, -4, -4, -4, 125, 124, 124, -96, -98, -98, 124, 123, 123, -108, -109, -109, 116, 114,
   114, 46, 44, 43, 66, 64, 64, -23, -23, -23, -72, -71, -72, -69, -71, -71, 90, 87, 87, 103, 102, 103, -102, -103, -103, -24, -26, -26, -36, -36,
   -36, 55, 53, 53, -117, -122, -122, -101, -105, -105, 103, 102, 102, 64, 62, 62, 124, 119, 119, 81, 79, 79, -19, -19, -19, -12, -12, -12, -13, -13,
   -13, -25, -25, -25, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 18, 124, -56, 2, 0, 36, 18, 122, -76, 0, 0, 2, -111, 65, -112, -3, -16, 0, -112, 16, 5, 18, 121,
   92, 0, 0, 0, 18, 122, 44, -112, -18, 24, -111, 9, 112, -105, -28, -64, -111, 62, 111, -111, 62, 98, 0, 2, 8, 18, 125, 108, 18, 125,
   68, 0, 0, -82, 2, 72, -3, 92, 82, -24, 21, 4, 104, 92, 82, -88, 18, 122, -66, 0, 0, 14, 0, 0, 87, 0, 0, 2, -80, 0,
   -82, -3, -20, 0, 0, 0, 0, 18, 122, 48, -3, -20, 0, 0, 0, 0, 8, 0, -82, 0, 0, 69, 90, -17, -120, 0, 0, -82, 0, 0,
   0, 0, 0, 3, 18, 123, 110, 0, 0, 0, 26, 20, 72, 18, 122, -64, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -20, -82, -111, 9,
   69, -111, 9, 78, 0, -90, -92, 18, 121, -104, 18, 125, 68, 18, 124, -40, -112, -18, 24, 18, 125, 108, 0, 0, 8, 18, 124, -24, -111, 64,
   46, 26, 20, 88, 18, 122, -64, -111, 9, 112, -105, -28, -64, -111, 64, -17, -111, 64, -69, 0, 0, 0, -3, -20, 0, 0, -128, 1, -112, -27,
   -27, -127, 14, 72, 0, 2, -88, 18, 122, -88, 18, 122, -80, 0, 0, 8, 0, 0, 14, 18, -126, -116, -17, 65, -48, 0, 0, 9, 0, 0,
   0, 0, 0, 0, 0, 0, -82, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 18, -126,
   -116, 2, -59, 23, 0, 2, -88, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 58, 5, 0, 2, -88, 0, 0, 0, 0, 0, 1, 0, 58,
   110, 18, -126, -116, 0, 0, 0, 0, 0, 1, 110, 111, -106, 18, -126, -116, 18, -126, -116, -17, 65, -48, 0, 1, 0, 0, 0, 9, 17, -49,
   -48, 26, -79, -95, 97, 103, 0, -50, -63, 84, 0, 83, -123, -7, -95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 1, 0, 71, 0,
   92, 70, 0, 73, 49, 0, 92, 92, 0, 54, 109, 0, 109, 115, 0, 45, 105, 0, 107, 102, 0, 112, 114, 0, 111, 97, 0, 119, -1, -1,
   -1, 33, -7, 4, 3, 0, 0, -1, 0, 44, 0, 0, 0, 0, 16, 0, 16, 0, 0, 8, -121, 0, -1, 9, 28, 72, -80, -96, -63, -125,
   85, 106, -60, 56, 88, 48, -61, -124, 12, 3, 24, 10, 76, 51, -29, -52, 25, 52, 17, 15, -50, -39, -110, -92, 70, 10, 23, 3, 22, 26,
   116, 49, 35, -119, 0, 1, 24, -72, 60, 17, 57, 112, 64, -126, 45, 48, -73, 76, -104, 80, 35, 7, -53, 127, 81, 114, -22, -44, 41, -128,
   -52, -51, 52, 13, -94, 52, 25, 58, -12, -54, 4, 25, 55, -1, 13, 48, 99, 6, 69, -126, -89, 48, 108, -80, 73, 42, -112, -62, -126, 18,
   101, 104, 48, 65, 33, -128, -22, 64, 18, 11, -32, 44, 41, -47, -64, -21, -64, 56, 76, 22, 48, -39, 97, -106, -96, 10, 36, 101, -38, 22,
   12, -110, 81, -94, -63, -128, 0, 59};


  //Java Resource String
   
  private static byte [] NEXT_STEP = {
   71, 73, 70, 56, 57, 97, 16, 0, 16, 0, -121, 0, 0, 115, 113, 113, 107, 105, 105, -120, -122, -122, -24, -24, -24, 126, 124, 124, 127, 125,
   125, 114, 113, 113, 117, 115, 115, 102, 99, 99, 96, 95, 95, 111, 110, 110, 66, 65, 65, 98, 97, 97, 120, 118, 118, -70, -71, -71, 73, 70,
   70, -99, -100, -100, 97, 96, 96, -109, -111, -111, 123, 122, 122, -74, -74, -74, 97, 94, 94, 96, 94, 94, 126, 123, 123, -123, -125, -125, -66, -68,
   -67, 120, 118, 119, -20, -22, -22, -74, -75, -75, 83, 81, 81, 106, 104, 104, 94, 90, 90, 92, 91, 91, 117, 118, 118, 108, 106, 106, -68, -68,
   -68, -123, -123, -122, 90, 88, 88, 84, 79, 80, -119, -121, -121, 100, 98, 98, -113, -115, -115, -50, -50, -50, 102, 101, 101, -96, -96, -96, -11, -11,
   -11, -93, -94, -94, -120, -121, -121, 95, 93, 93, -26, -26, -26, -84, -87, -87, 111, 111, 111, 87, 84, 84, -110, -110, -110, 114, 111, 111, 127, 127,
   126, -105, -107, -107, -51, -55, -53, -8, -8, -8, 108, 103, 105, -73, -74, -73, 125, 123, 123, -80, -81, -81, 83, 80, 80, 83, 83, 83, -100, -100,
   -100, 125, 125, 125, 84, 79, 79, -117, -119, -119, -108, -111, -111, -26, -25, -25, -101, -103, -103, 54, 54, 55, -119, -120, -120, 92, 89, 89, 78, 75,
   75, 85, 83, 83, 109, 106, 106, -127, -128, -128, -36, -38, -38, 116, 115, 115, 116, 113, 113, -81, -83, -83, 114, 111, 113, 123, 123, 123, -5, -5,
   -5, 90, 87, 86, 105, 103, 103, 67, 66, 66, 103, 101, 101, -4, -4, -4, 125, 124, 124, -96, -98, -98, 124, 123, 123, -108, -109, -109, 116, 114,
   114, 46, 44, 43, 66, 64, 64, -23, -23, -23, -72, -71, -72, -69, -71, -71, 90, 87, 87, 103, 102, 103, -102, -103, -103, -24, -26, -26, -36, -36,
   -36, 55, 53, 53, -117, -122, -122, -101, -105, -105, 103, 102, 102, 64, 62, 62, 124, 119, 119, 81, 79, 79, -19, -19, -19, -12, -12, -12, -13, -13,
   -13, -25, -25, -25, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
   0, 0, 0, 0, 0, 0, 0, 18, 124, -56, 2, 0, 36, 18, 122, -76, 0, 0, 2, -111, 65, -112, -3, -16, 0, -112, 16, 5, 18, 121,
   92, 0, 0, 0, 18, 122, 44, -112, -18, 24, -111, 9, 112, -105, -28, -64, -111, 62, 111, -111, 62, 98, 0, 2, 8, 18, 125, 108, 18, 125,
   68, 0, 0, -82, 2, 72, -3, 92, 82, -24, 21, 4, 104, 92, 82, -88, 18, 122, -66, 0, 0, 14, 0, 0, 87, 0, 0, 2, -80, 0,
   -82, -3, -20, 0, 0, 0, 0, 18, 122, 48, -3, -20, 0, 0, 0, 0, 8, 0, -82, 0, 0, 69, 90, -17, -120, 0, 0, -82, 0, 0,
   0, 0, 0, 3, 18, 123, 110, 0, 0, 0, 26, 20, 72, 18, 122, -64, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -20, -82, -111, 9,
   69, -111, 9, 78, 0, -90, -92, 18, 121, -104, 18, 125, 68, 18, 124, -40, -112, -18, 24, 18, 125, 108, 0, 0, 8, 18, 124, -24, -111, 64,
   46, 26, 20, 88, 18, 122, -64, -111, 9, 112, -105, -28, -64, -111, 64, -17, -111, 64, -69, 0, 0, 0, -3, -20, 0, 0, -128, 1, -112, -27,
   -27, -127, 14, 72, 0, 2, -88, 18, 122, -88, 18, 122, -80, 0, 0, 8, 0, 0, 14, 18, -126, -116, -17, 65, -48, 0, 0, 9, 0, 0,
   0, 0, 0, 0, 0, 0, -82, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 18, -126,
   -116, 2, -59, 23, 0, 2, -88, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 58, 5, 0, 2, -88, 0, 0, 0, 0, 0, 1, 0, 58,
   110, 18, -126, -116, 0, 0, 0, 0, 0, 1, 110, 111, -106, 18, -126, -116, 18, -126, -116, -17, 65, -48, 0, 1, 0, 0, 0, 9, 17, -49,
   -48, 26, -79, -95, 97, 103, 0, -50, -63, 84, 0, 83, -123, -7, -95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 12, 0, 1, 0, 71, 0,
   92, 70, 0, 73, 49, 0, 92, 92, 0, 54, 109, 0, 109, 115, 0, 45, 105, 0, 107, 102, 0, 112, 114, 0, 111, 97, 0, 119, -1, -1,
   -1, 33, -7, 4, 3, 0, 0, -1, 0, 44, 0, 0, 0, 0, 16, 0, 16, 0, 0, 8, -112, 0, -1, 9, 28, 72, -80, -96, -64, 24,
   53, -86, 24, 92, 56, 32, -61, -124, 12, 11, 11, 14, 64, 115, -28, -52, -116, 52, 17, 5, -46, -39, -32, 34, 69, -111, 36, 91, -26, 68,
   -92, -13, -124, 11, 6, 1, 2, -120, 40, 96, -79, -112, 78, 14, 9, 93, 46, 16, 32, 80, -96, 64, -126, 13, 5, -23, -112, 57, 97, 0,
   -128, 79, -97, 95, 14, 68, 33, 72, 71, 70, -113, 43, 1, -110, 122, 104, 114, -96, 1, -58, -127, 116, -40, -40, -128, -111, -64, 66, 5, 20,
   89, -52, -32, 36, -70, 6, 1, 19, 26, 101, 74, 44, -96, -48, -14, -115, -110, 37, 112, 22, -112, -48, 50, 114, -57, -113, 48, 64, -30, 100,
   -4, 23, -61, 10, 18, 21, 115, 5, 14, 8, -110, -73, -81, -63, -128, 0, 59};

  public static ImageView getPriorImage ()
  {
    return  createImageView (PRIOR_STEP);
  
  }     
  
  public static ImageView getNextImage ()
  {
    return createImageView (NEXT_STEP);
    
  }     

  public static Image createImage (byte[] b) 
  {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        Image result=null;
        try
        {
            result = new Image (bais);
        }
        catch (Exception ex)
        {
            
        }
        return result;
    }    
    
    public static ImageView createImageView (byte [] b)
    {
        ImageView result = new ImageView (createImage (b));
        return result;
    }      
    
   
}


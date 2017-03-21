package com.example.joseluissanchez_porrogodoy.agrogest.ui.customviews;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which has ViewUtility methods
 *
 */
public class ViewUtility extends CommonUtils {
    private static Pattern pattern;
    private static Matcher matcher;
    //Email Pattern
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validate Email with regular expression
     *
     * @param email
     * @return true for Valid Email and false for Invalid Email
     */
    public static boolean validate(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();

    }
    /**
     * Checks for Null String object
     *
     * @param txt
     * @return true for not null and false for null String object
     */
    public static boolean isNotNull(String txt){
        //return txt!=null && txt.trim().length()>0 ? true: false;
        return (txt!=null && txt.trim().length()>0);
    }

    @Deprecated
    public static String dateToString (String formato, Date fecha) {
        return new SimpleDateFormat(formato).format(fecha);
    }

    @Deprecated
    public static Date stringToDate (String formato, String stringFecha) {
        DateFormat formatoFecha = new SimpleDateFormat(formato);
        Date fecha = null;
        try {
            fecha = formatoFecha.parse(stringFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha;
    }





    /**
     * No está funcionando correctamente el reciclado. No se elimina por si realmente hacer falta más adelante
      */
    /*public static void reciclarImagen (ImageView vista) {
        if (vista!=null) {
            BitmapDrawable bd = (BitmapDrawable) vista.getDrawable();
            if (bd != null) {
                Bitmap bitmap = bd.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                vista.setImageBitmap(null);
            }
        }
    }*/







}
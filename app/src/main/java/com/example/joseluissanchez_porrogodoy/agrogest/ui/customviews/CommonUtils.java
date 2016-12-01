/**
 * 
 */
package com.example.joseluissanchez_porrogodoy.agrogest.ui.customviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 * Generic class with utility methods and constants
 */
public class CommonUtils {
	/**
	 * TextFiler for alphabetic and spaces
	 */
	public static final InputFilter alphabeticFilter=new InputFilter() {
	        public CharSequence filter(CharSequence src, int start,
									   int end, Spanned dst, int dstart, int dend) {
	            if(src.equals(CommonConstants.STRING_VOID)){ // for backspace
	                return src;
	            }
	            if(src.toString().matches("[a-zA-Z �����A���򄖂�&]+")){
	                return src;
	            }
	            return CommonConstants.STRING_VOID;
	        }
    	};
	/**
	 * TextFiler for hexadecimal numbers
	 */
	public static final InputFilter hexadecimalFilter=new InputFilter() {
	        public CharSequence filter(CharSequence src, int start,
									   int end, Spanned dst, int dstart, int dend) {
	            if(src.equals(CommonConstants.STRING_VOID)){ // for backspace
	                return src;
	            }
	            if(src.toString().matches("[0-9a-fA-F]+")){
	                return src;
	            }
	            return CommonConstants.STRING_VOID;
	        }
    	};
	/**
	* Hide the keyboard 
	* 
	* @param ctx	- Context of application
	* @param view	- View
	*/
	public static void hideKeyboard(Context ctx, View view){
		InputMethodManager imm = (InputMethodManager)ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/**
	 * Shows a simple dialog for a YES/NO answer
	 * 
	 * @param context	- The context
	 * @param message	- The message to show
	 * @param listener	- The DialogListener
	 * 
	 */
	public static void showConfirmationDialog(Context context, String message, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(android.R.string.yes, listener)
				.setNegativeButton(android.R.string.no, listener);
		builder.create().show();;
	}
	/**
	 * Shows a simple dialog with a simple message
	 * 
	 * @param context	- The context
	 * @param message	- The message
	 * @param listener	- The DialogListener
	 * 
	 */
	public static void showInformativeDialog(Context context, String message, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setCancelable(false)
				.setNeutralButton(android.R.string.ok, listener);
		builder.create().show();
	}

	/**
	 * Shows a simple dialog with a simple message
	 *
	 * @param context	- The context
	 * @param message	- The message
	 *
	 */
	public static void showAutoDismisibleInformativeDialog(Context context, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setCancelable(false)
				.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});
		builder.create().show();
	}


	/**
	 * Shows a dialog to manage a simple date picker
	 *
	 * @param context	- The context
	 * @param date		- The date
	 * @param dateListener	- Listener to manage the date change events
	 */
	public static void showDatePicker(Context context, Date date, OnDateSetListener dateListener) {
		Calendar cal= Calendar.getInstance();
		if(date!=null) {
			cal.setTime(date);
		}
		DatePickerDialog dpd=new DatePickerDialog(context, dateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		dpd.show();
	}
	/**
	 * Shows a dialog to manage a simple date picker
	 *
	 * @param context	- The context
	 * @param date		- The date
	 * @param dateListener	- Listener to manage the date change events
	 * @param minDate
	 * @param maxDate
	 */
	public static void showDatePicker(Context context, Date date, OnDateSetListener dateListener, Date minDate, Date maxDate) {
		Calendar cal= Calendar.getInstance();
		if(date!=null) {
			cal.setTime(date);
		}
		DatePickerDialog dpd=new DatePickerDialog(context, dateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		if(minDate!=null) {
			dpd.getDatePicker().setMinDate(minDate.getTime());
		}
		if(maxDate!=null) {
			dpd.getDatePicker().setMaxDate(maxDate.getTime());
		}
		dpd.show();
	}

	/**
	 * Shows a dialog to manage a simple date picker
	 *
	 * @param context	- The context
	 */
	public static void showTimePicker(Context context, TimePickerDialog.OnTimeSetListener timeListener) {

		int hour = 0;
		int minute = 0;

		TimePickerDialog dpd=new TimePickerDialog(context, timeListener, hour, minute, true);
		dpd.show();
	}
	/**
	 * Shows a dialog to manage a simple date picker
	 *
	 * @param context	- The context
	 */
	public static void showTimePicker(Context context, int hour, int minute, TimePickerDialog.OnTimeSetListener timeListener) {

		TimePickerDialog dpd=new TimePickerDialog(context, timeListener, hour, minute, true);
		dpd.show();
	}
	/**
	 * Builds a multi-choice dialog
	 * 
	 * @param context	- The context
	 * @param title		- The title of the dialog
	 * @param options	- The array of options
	 * @param listener	- The Listener
	 * 
	 * @return The new built AlertDialog
	 */
	public static AlertDialog multipleOptionsDialog(Context context, String title, CharSequence[] options, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setItems(options, listener);
		return builder.create();
	}
	/**
	 * Add a InputFilter to an array of InputFilter
	 * 
	 * @param filters		- The original list of filters
	 * @param newFilter		- The new filter to add
	 * 
	 * @return The new array with the all filters plus the new one
	 */
	public static InputFilter[] addFilter(InputFilter[] filters, InputFilter newFilter) {
		//Creamos el nuevo array
		InputFilter[] newArray;
		if(filters!=null) {
			newArray=new InputFilter[filters.length+1];
			//Copiamos los valores originales
			System.arraycopy(filters, 0, newArray, 0, filters.length);
		} else {
			newArray=new InputFilter[1];
		}
		
		//Incluimos el nuevo valor
		newArray[newArray.length-1] = newFilter;
		//Devolvemos el array final
		return newArray;
	}
	/**
	 * Starts the default select ivImage Activity when user can choose between select a ivImage from the Gallery or
	 * take a photo with the Camera (or any other App that allows take photos or select files)
	 * 
	 * @param act			- The Activity who made the request
	 * @param title			- The title for the chooser
	 * @param requestCode	- The request code
	 * 
	 * @return	- true	 if the intent is successfully launched
	 * 			- false	if no app can perform this action 
	 */
	public static boolean startPickImageActivityForResult(Activity act, String title, int requestCode) {
		//The intent for the gallery
		Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
	    galleryIntent.setType("ivImage/*");
	    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
	    //The intent for the camera
	    Intent pickIntent = new Intent(Intent.ACTION_PICK);
	    pickIntent.setType("image/*");
	    pickIntent.setAction(Intent.ACTION_GET_CONTENT);
	    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, getRandomTempFile(act, CommonConstants.EXTENSION_JPG));

	    //Define the chooser
	    Intent chooser = Intent.createChooser(pickIntent, title);
	    //Additional extras
	    Intent[] intentArray =  {cameraIntent, galleryIntent};
	    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
	    //Start the chooser
	    if (chooser.resolveActivity(act.getPackageManager()) != null) {
	    	act.startActivityForResult(chooser,requestCode);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Launches the default crop image intent so user can pick the app to crop a image
	 * 
	 * @param act			- The activity who launch the intent
	 * @param title			- the title for the chooser
	 * @param requestCode	- The request code
	 * @param uri			- The uri to the image
	 * @param width			- The width selected for the image
	 * @param height		- The height selected for the image
	 * 
	 * @return	- true	 if the intent is successfully launched
	 * 			- false	if no app can perform this action 
	 */
	public static boolean startCropImageActivity(Activity act, String title, int requestCode, Uri uri, int width, int height){
		//call the standard crop action intent (the user device may not support it)
		Intent cropIntent = new Intent("com.android.camera.action.CROP");
		    //indicate image type and Uri
		cropIntent.setDataAndType(uri, "image/*");
		    //set crop properties
		cropIntent.putExtra("crop", "true");
		    //indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		    //indicate output X and Y
		cropIntent.putExtra("outputX", 256);
		cropIntent.putExtra("outputY", 256);
		    //retrieve data on return
		cropIntent.putExtra("return-data", true);
		if (cropIntent.resolveActivity(act.getPackageManager()) != null) {
			act.startActivityForResult(cropIntent, requestCode);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Creates a unique subdirectory of the designated app cache directory. Tries to use external
	 * but if not mounted, falls back on internal storage.
	 * 
	 * @param context		- The Context
	 * @param uniqueName	- A unique external name or null if not desired
	 * 
	 * @return The File directory to the cache directory
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
	    // Check if media is mounted or storage is built-in, if so, try and use external cache directory
	    // otherwise use internal cache directory
	    final String cachePath =
	            Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
	                    !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
	                            context.getCacheDir().getPath();

	    return uniqueName!=null?new File(cachePath + File.separator + uniqueName): new File(cachePath);
	}
	/**
	 * Method to simplify the String.format(format,...) and getString(resource) in a single line
	 * 
	 * @param context	- The context to get the resource from
	 * @param resource	- The resource with the format
	 * @param args		- The arguments to use with the String.format() method
	 * 
	 * @return	A String with the formatted text
	 */
	public static String format(Context context, int resource, Object...args) {
		return String.format(context.getString(resource), args);
	}
	/**
	 * Return a random file for the app in the external storage directory
	 * 
	 * @param context	- The context
	 * @param extension	- The extension for the file
	 * 
	 * @return	A file
	 */
	public static File getRandomTempFile(Context context, String extension) {
	   // Only one time will we grab this location.
	   final File path = new File(Environment.getExternalStorageDirectory(),
	         context.getString(context.getApplicationInfo().labelRes));
	   //
	   // If this does not exist, we can create it here.
	   if (!path.exists()) {
	      path.mkdir();
	   }
	   //
	   return new File(path, String.valueOf(System.currentTimeMillis()) + CommonConstants.STRING_POINT + extension!=null?extension: CommonConstants.STRING_VOID);
	}
}

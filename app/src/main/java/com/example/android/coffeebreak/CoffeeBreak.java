package com.example.android.coffeebreak;


import android.content.Intent;
import android.net.Uri;
import  android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.coffeebreak.R;

import java.text.NumberFormat;


/**
 * This app display an order form to order coffee
 */
public class CoffeeBreak extends ActionBarActivity
{
    TextView coffeeCount;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coffeeCount = (TextView) findViewById(R.id.quantity_text_view);
    }
    /**
     * This method is called when the plus button is clicked
     */
    public void increment(View view)
    {
        if (quantity == 100)
        {
            Toast.makeText(this,"You cannot order more than 100 cups of coffee",Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity+ 1;
        display(quantity);

    }
    /**
     * This method is called when theminus button is clicked
     */
    public void decrement(View view) {
        if(quantity == 1)
        {
            Toast.makeText(this,"You cannot order zero cups of coffee",Toast.LENGTH_LONG).show();
            return;
        }

        quantity = quantity - 1;
        display(quantity);

    }


    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view)
    {
        EditText namefield = (EditText) findViewById(R.id.name_id);
        String name = namefield.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT," just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);

        Log.d("if ","starting");

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
        else
        {
            Log.d("Error: ","Couldnt start");
        }

        Log.d("if ","done");



    }
    /*
    *calculate the price of the order
     */
    private int calculatePrice(boolean AddwhippedCream,boolean Addchocolate )
    {
        int basePrice = 5;

        if (AddwhippedCream)
        {
            basePrice = basePrice + 1;
        }
        if (Addchocolate)
        {
            basePrice = basePrice + 2;
        }
        int price = quantity * basePrice;
        return price;
    }

    private String createOrderSummary(String name,int price,boolean AddwhippedCream,boolean Addchocolate )
    {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream? " + AddwhippedCream;
        priceMessage = priceMessage + "\nAddchocolate? " + Addchocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: R" + price;
        priceMessage = priceMessage + "\nThank you";
        return priceMessage;
    }

    /*
    *This method display the given quantity value on the screen
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}







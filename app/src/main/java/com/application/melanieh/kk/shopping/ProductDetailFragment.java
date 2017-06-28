package com.application.melanieh.kk.shopping;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.checkout.CheckoutActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by melanieh on 6/5/17.
 */

public class ProductDetailFragment extends Fragment {

    ImageView productImage;
    TextView productName;
    TextView cost;
    Spinner varietySpinner;
    TextView qtyLabel;
    EditText qtyValue;
    TextView custNotesLabel;
    EditText custNotesET;

    InputMethodManager imm;
    private int variety = 0;

    @OnClick({R.id.qty_value,R.id.cust_requests_notes})
    public void onClick(View view) {
        showKeyboard(view);
    }

    public ProductDetailFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_cart:
                Intent launchCartView = new Intent(getContext(), CheckoutActivity.class);
//                launchCartView.putExtra(Constants.CART_ITEMS_KEY, cartItems);
                startActivity(launchCartView);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView:");

        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        productImage = (ImageView)rootView.findViewById(R.id.product_iv);
        productName = (TextView)rootView.findViewById(R.id.name);
        cost = (TextView)rootView.findViewById(R.id.cost);
        varietySpinner = (Spinner) rootView.findViewById(R.id.product_variety_spinner);
        qtyLabel = (TextView)rootView.findViewById(R.id.qty_label);
        qtyValue = (EditText) rootView.findViewById(R.id.qty_value);
        custNotesLabel = (TextView)rootView.findViewById(R.id.cust_notes_label);
        custNotesET = (EditText) rootView.findViewById(R.id.cust_requests_notes);

        //control visibility of keyboard to only show when customer notes and quantity fields are clicked on
        imm = (InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        // default state for keyboard is hidden
        imm.hideSoftInputFromWindow(qtyValue.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(custNotesET.getWindowToken(), 0);

        productImage.setImageResource(R.drawable.candle_category_sample);
        productName.setText("Tealights");
        cost.setText("$1");
        loadSpinner();
        return rootView;
    }

    /**
     * Setup the dropdown spinner containing the available varieties of the product.
     */
    private void loadSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        Resources res = getResources();
        String[] items = res.getStringArray(R.array.candle_varieties);
        List<String> spinnerItems = Arrays.asList(items);

        ArrayAdapter varietySpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.candle_varieties, android.R.layout.simple_spinner_dropdown_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        varietySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Bind the adapter to the spinner
        varietySpinner.setAdapter(varietySpinnerAdapter);

        // Set the integer mSelected to the constant values
        varietySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {

                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                variety = Constants.VARIETY_UNKNOWN;
            }
        });
    }

    private void showKeyboard(View view) {
        // reveal keyboard for these
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

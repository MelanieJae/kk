package com.application.melanieh.kk.shopping;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.application.melanieh.kk.Constants;
import com.application.melanieh.kk.KKApplication;
import com.application.melanieh.kk.R;
import com.application.melanieh.kk.models_and_modules.CartItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.ReplaySubject;
import timber.log.Timber;

/**
 * Created by melanieh on 6/20/17.
 *
 * As of 2-26-18, all pay button fragments print and e-mail invoice only until Stripe/Google Pay
 * code is implemented
 */

public class PayWithStripeBtnFragment extends Fragment {

    @OnClick(R.id.pay_with_stripe_btn)
    public void onClick(View view) {
        Timber.d("PayWithStripeBtn: onClick()");
        // check runtime permissions
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // show rationale for permission request
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(),
                        "You need to grant external storage permission to view and" +
                                "save your invoice PDF", Toast.LENGTH_LONG)
                        .show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        Constants.REQUEST_CODE_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            createInvoicePDF();

        }

    }

    public static PayWithStripeBtnFragment newInstance() {
        PayWithStripeBtnFragment fragment = new PayWithStripeBtnFragment();
        return fragment;
    }

    public PayWithStripeBtnFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stripe_only_pay_button, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @TargetApi(19)
    private void createInvoicePDF() {
        Timber.d("createInvoicePDF()");

        ArrayList<CartItem> cart = retrieveAllCartItems();
        // create new pdf doc
        PdfDocument document = new PdfDocument();

        // create a 'quadrant' for page content
        PdfDocument.PageInfo pageDesc =
                new PdfDocument.PageInfo.Builder(100, 200, 1)
                    .create();

        // start a page
        PdfDocument.Page page = document.startPage(pageDesc);

        // add shopping cart items to the page
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(4f);
//        canvas.drawCircle(50, 50, 30, paint);

//        for (int i = 0; i < cart.size(); i++) {
//            int lineOffset = 10 * i;
//            int itemOffset =  i * (lineOffset + 60);
//            int yCoord = lineOffset + itemOffset;
            canvas.drawText("Item: " + cart.get(0).getItemName(), 5  , 5, paint);
            canvas.drawText("Variety:" + cart.get(0).getItemVariety(), 5  , 10, paint);
            canvas.drawText("Qty:" + cart.get(0).getItemQty(), 5  , 20, paint);
            canvas.drawText("Unit Price: " + cart.get(0).getItemUnitPrice(), 5  , 30, paint);
            canvas.drawText("Total Price: " + cart.get(0).getItemUnitPrice() * cart.get(0).getItemQty(),
                    5  , 40, paint);

//        }

            // finish the page
            document.finishPage(page);

        // add another page for tax, etc line items, subtotal and invoice total

            // create a 'quadrant' for page content
            PdfDocument.PageInfo page2Desc = new PdfDocument.PageInfo.Builder(100, 100, 2)
                    .create();
            PdfDocument.Page page2 = document.startPage(page2Desc);
            Canvas canvas2 = page2.getCanvas();
            Paint paint2 = new Paint();
            paint2.setColor(Color.BLUE);
            canvas2.drawCircle(50, 50, 30, paint2);
            document.finishPage(page2);

            // write the document content
            // file path
            try {
                document.writeTo(new FileOutputStream(
                        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                "invoice.pdf")));
                // TODO:
                // 1. add AlertDialog directing user to the invoice in their device
                // (i.e. the downloads directory)
                // 2. navigate up backstack for back arrow from the invoice.pdf Drive screen or
                // the finder back to the checkout screen to initiate a "submit the order" button fragment
                // or allow the user to continue shopping. Clicking submit the order will
                // send a copy of the invoice to Kim.

                // to be replaced by AlertDialog
//                Toast.makeText(getActivity(), "PDF created",
//                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "IO Exception: " + e.toString(),
                        Toast.LENGTH_LONG).show();
            }
            // close document
            document.close();
        }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.d("onActivityResult()");

        if (requestCode == Constants.REQUEST_CODE_WRITE_EXTERNAL_STORAGE
                && resultCode == getActivity().RESULT_OK) {
            createInvoicePDF();
        }
    }

    private ArrayList<CartItem> retrieveAllCartItems() {
        Timber.d("retrieveAllCartItems()");

        ArrayList<CartItem> cartItems = new ArrayList<>();
        ReplaySubject<CartItem> cartItemObservable = KKApplication.getObservable();
        Timber.d("cartItemObservable: " + cartItemObservable.toString());
        Consumer<CartItem> cartItemConsumer = new Consumer<CartItem>() {
            @Override
            public void accept(CartItem cartItem) throws Exception {
                cartItems.add(cartItem);

            }
        };

        cartItemObservable.subscribe(cartItemConsumer);
        return cartItems;
    }



}

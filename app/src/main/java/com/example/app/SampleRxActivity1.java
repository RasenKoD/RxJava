package com.example.app;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.app.databinding.ActivitySampleRx1Binding;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class SampleRxActivity1 extends AppCompatActivity {

    ActivitySampleRx1Binding rx1Binding;
    Observable<String> myObservable;
    Observer<String> myObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Activity 1");
        createObservableAndObserver();

        rx1Binding = DataBindingUtil.setContentView(this, R.layout.activity_sample_rx1);

        rx1Binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myObservable.subscribe(myObserver);
            }
        });
    }

    private void createObservableAndObserver() {
        myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext(rx1Binding.editText.getText().toString());
                        sub.onCompleted();
                    }
                }
        );

        myObserver = new Observer<String>() {

            @Override
            public void onCompleted() {
                rx1Binding.button.setText("Okay");

            }

            @Override
            public void onError(Throwable e) {
                if(e instanceof EmptyStackException) {
                    rx1Binding.button.setText("nice to meetya");
                    setTitle("Ugh");
                }
            }

            @Override
            public void onNext(String text) {

                rx1Binding.textView.setText(text);

                rx1Binding.textViewWithFun.setText(text+"SUUPAR");
                throw new EmptyStackException();
            }
        };

    }
}

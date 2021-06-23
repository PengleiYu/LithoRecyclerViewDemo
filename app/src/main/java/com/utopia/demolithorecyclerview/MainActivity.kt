package com.utopia.demolithorecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.utopia.demolithorecyclerview.spec.ProgressLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
        val c: ComponentContext = ComponentContext(this);
        val component: Component = ProgressLayout.create(c).build()

        val lithoView: LithoView = LithoView.create(c, component);
        setContentView(lithoView);
    }
}
package com.utopia.demolithorecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.LithoView
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.utopia.demolithorecyclerview.spec.ListSection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val c = ComponentContext(this)
        val component: Component =
            RecyclerCollectionComponent.create(c)
                .section(ListSection.create(SectionContext(c)))
                .build()

        val lithoView: LithoView = LithoView.create(c, component);
        setContentView(lithoView);
    }
}
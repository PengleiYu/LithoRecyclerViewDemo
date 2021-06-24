package com.utopia.demolithorecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.facebook.litho.Component
import com.facebook.litho.LithoView
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.widget.RecyclerCollectionComponent
import com.utopia.demolithorecyclerview.spec.ListSection

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_main)
        val c = SectionContext(this)
        val component: Component =
//            ProgressLayout.create(c).build()
            RecyclerCollectionComponent.create(c)
                .section(ListSection.create(c))
                .build()

        val lithoView: LithoView = LithoView.create(c, component);
        setContentView(lithoView);
    }
}
package eu.darken.sdmse.analyzer.ui.storage.storage.categories

import android.text.format.Formatter
import android.view.ViewGroup
import eu.darken.sdmse.R
import eu.darken.sdmse.analyzer.core.device.DeviceStorage
import eu.darken.sdmse.analyzer.core.storage.categories.AppCategory
import eu.darken.sdmse.analyzer.ui.storage.storage.StorageContentAdapter
import eu.darken.sdmse.common.lists.binding
import eu.darken.sdmse.databinding.AnalyzerStorageVhAppsBinding


class AppCategoryVH(parent: ViewGroup) :
    StorageContentAdapter.BaseVH<AppCategoryVH.Item, AnalyzerStorageVhAppsBinding>(
        R.layout.analyzer_storage_vh_apps,
        parent
    ) {

    override val viewBinding = lazy { AnalyzerStorageVhAppsBinding.bind(itemView) }

    override val onBindData: AnalyzerStorageVhAppsBinding.(
        item: Item,
        payloads: List<Any>
    ) -> Unit = binding { item ->
        val storage = item.storage
        val content = item.content

        val usedText = Formatter.formatShortFileSize(context, content.spaceUsed)
        val totalPercent = ((content.spaceUsed / storage.spaceUsed.toDouble()) * 100).toInt()
        usedSpace.text = getString(R.string.analyzer_space_used, usedText)
        progress.progress = totalPercent

        root.setOnClickListener { item.onItemClicked(content) }
    }

    data class Item(
        val storage: DeviceStorage,
        val content: AppCategory,
        val onItemClicked: (AppCategory) -> Unit,
    ) : StorageContentAdapter.Item {

        override val stableId: Long = this.javaClass.hashCode().toLong()
    }

}
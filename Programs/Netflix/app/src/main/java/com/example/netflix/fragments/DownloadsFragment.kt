package com.example.netflix.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflix.Constants
import com.example.netflix.MyApp
import com.example.netflix.Preferences
import com.example.netflix.R
import com.example.netflix.activities.PlayerActivity
import com.example.netflix.adapters.DownloadAdapter
import com.example.netflix.databinding.FragmentDownloadBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.models.AppModel
import com.example.netflix.models.MoviesModel
import com.example.netflix.objects.DownloadState
import com.google.android.exoplayer2.util.Log
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet


class DownloadsFragment : ParentFragment() {
    private var _binding: FragmentDownloadBinding? = null
    private val downloadAdapter = DownloadAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = _binding
        if (binding != null) {
            binding.toolbar.setOnMenuItemClickListener(toolbarListener)
            binding.toolbar.inflateMenu(R.menu.download_menu_items)
            val layoutManager = LinearLayoutManager(requireContext())
            downloadAdapter.mList = Preferences.instance.downloads
            downloadAdapter.mClickListener = object : ItemClickedListener {
                override fun onItemClick(view: View, model: AppModel) {
                    super.onItemClick(view, model)
                    startPlayerActivity(model as MoviesModel, true)
                }
            }
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = downloadAdapter

        }
        downloadAdapter.mMenuClickListener = object : ItemClickedListener {
            override fun onItemClick(view: View, model: AppModel) {
                val pos = downloadAdapter.mList.indexOf(model as MoviesModel)
                if (model.isDownloaded) {
                    showExtraListWhenDownloaded(model, pos)
                } else {
                    showExtraList(model, pos)

                }
            }
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    private fun showExtraList(model: MoviesModel, pos: Int) {
        var stateString = ""
        var drawable = resources.getDrawable(R.drawable.ic_download, requireContext().theme)
        if (model.downloadState == DownloadState.PAUSED) {
            stateString = "Resume Download"
        } else {
            drawable = resources.getDrawable(R.drawable.ic_pause, requireContext().theme)
            stateString = "Pause Download"
        }
        val sheet = OptionsSheet().build(requireContext()) {
            title(model.title)
            displayMode(DisplayMode.LIST)
            with(
                Option(drawable, stateString),
                Option(R.drawable.ic_delete, "Delete from downloads"),

                )
            onPositive { index: Int, _: Option ->
                when (index) {
                    0 -> {
                        if (model.downloadState == DownloadState.PAUSED) {
                            downloadAdapter.mList[pos].downloadState = DownloadState.RESUMED
                        } else {
                            downloadAdapter.mList[pos].downloadState = DownloadState.PAUSED
                        }
                        downloadAdapter.notifyDataSetChanged()

                    }
                    1 -> {
                        downloadAdapter.mList[pos].shouldDelete = true
                        val item = Preferences.instance.downloads.find { it.id == model.id }
                        Preferences.instance.downloadIdentity.remove(item?.id)
                        MyApp.instance.removeMovie(model.id)
                        downloadAdapter.mList = Preferences.instance.downloads
                        downloadAdapter.notifyDataSetChanged()
                    }


                }
            }
        }
        sheet.preventIconTint(false)
        sheet.displayToolbar(true)
        sheet.displayCloseButton(false)
        sheet.isCancelable = true
        sheet.show()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    fun showExtraListWhenDownloaded(model: MoviesModel, pos: Int) {
        var stateString = ""
        var drawable = resources.getDrawable(R.drawable.ic_download, requireContext().theme)
        if (model.downloadState == DownloadState.PAUSED) {
            stateString = "Resume Download"
        } else {
            drawable = resources.getDrawable(R.drawable.ic_pause, requireContext().theme)
            stateString = "Pause Download"
        }
        val sheet = OptionsSheet().build(requireContext()) {
            title(model.title)
            displayMode(DisplayMode.LIST)
            with(
                Option(R.drawable.ic_delete, "Delete from downloads"),
            )
            onPositive { index: Int, _: Option ->
                when (index) {
                    0 -> {
                        downloadAdapter.mList[pos].shouldDelete = true
                        val item = Preferences.instance.downloads.find { it.id == model.id }
                        Preferences.instance.downloadIdentity.remove(item?.id)
                        MyApp.instance.removeMovie(model.id)
                        downloadAdapter.mList = Preferences.instance.downloads
                        downloadAdapter.notifyDataSetChanged()
                        val v = Preferences.instance.downloads
                    }
                }
            }
        }
        sheet.preventIconTint(false)
        sheet.displayToolbar(true)
        sheet.displayCloseButton(false)
        sheet.isCancelable = true
        sheet.show()
    }

    private var toolbarListener =
        Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.open_browser -> {

                }
                R.id.cast -> {
                    Log.d("", "")

                }
                else -> {}
            }
            false
        }

    private fun startPlayerActivity(moviesModel: MoviesModel, play: Boolean) {
        val intent = Intent(requireActivity(), PlayerActivity::class.java)
        intent.putExtra(Constants.EXTRA_MOVIE_MODEL, moviesModel)
        intent.putExtra(Constants.SHOULD_PLAY, play)
        startActivity(intent)


    }

}
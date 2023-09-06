package com.example.netflix.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.mediarouter.app.MediaRouteButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.netflix.*
import com.example.netflix.R
import com.example.netflix.activities.*
import com.example.netflix.adapters.CategoriesAdapter
import com.example.netflix.adapters.ContinueWatchingAdapter
import com.example.netflix.adapters.MoviesItemAdapter
import com.example.netflix.databinding.FragmentHomeBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.models.AppModel
import com.example.netflix.models.CategoriesModel
import com.example.netflix.models.MoviesModel
import com.example.netflix.objects.NAV
import com.google.android.gms.cast.MediaLoadRequestData
import com.google.android.gms.cast.framework.*
import com.google.android.gms.cast.framework.IntroductoryOverlay.OnOverlayDismissedListener
import com.google.android.gms.cast.framework.media.RemoteMediaClient
import com.maxkeppeler.sheets.options.DisplayMode
import com.maxkeppeler.sheets.options.Option
import com.maxkeppeler.sheets.options.OptionsSheet


class HomeFragment : ParentFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val moviesAdapter = MoviesItemAdapter()
    private val wishListAdapter = MoviesItemAdapter()
    private val continueAdapter = ContinueWatchingAdapter()
    private var mCastSession: CastSession? = null
    private var mCastContext: CastContext? = null
    private var mCastStateListener: CastStateListener? = null
    private var mIntroductoryOverlay: IntroductoryOverlay? = null
    private val mSessionManagerListener: SessionManagerListener<CastSession> =
        SessionManagerListenerImpl()

    private var isShowing = true
    var isWishListed = false
        @SuppressLint("UseCompatLoadingForDrawables")
        set(value) {
            field = value
            if (value) {
                val drawable = resources.getDrawable(
                    R.drawable.avd_cross_to_tick,
                    requireContext().theme
                ) as AnimatedVectorDrawable
                _binding?.listImage?.setImageDrawable(drawable)
                drawable.start()
            } else {
                val drawable = resources.getDrawable(
                    R.drawable.avd_tick_to_cross,
                    requireContext().theme
                ) as AnimatedVectorDrawable
                _binding?.listImage?.setImageDrawable(drawable)
                drawable.start()
            }
        }
    var posterModel = MoviesModel()

    inner class SessionManagerListenerImpl : SessionManagerListener<CastSession> {
        override fun onSessionEnded(session: CastSession, p1: Int) {
            if (session === mCastSession) {
                mCastSession = null
            }
        }

        override fun onSessionEnding(p0: CastSession) {
        }

        override fun onSessionResumeFailed(p0: CastSession, p1: Int) {
        }

        override fun onSessionResumed(session: CastSession, p1: Boolean) {
            mCastSession = session
        }

        override fun onSessionResuming(p0: CastSession, p1: String) {
        }

        override fun onSessionStartFailed(p0: CastSession, p1: Int) {
        }

        override fun onSessionStarted(session: CastSession, p1: String) {
            mCastSession = session
        }

        override fun onSessionStarting(p0: CastSession) {
        }

        override fun onSessionSuspended(p0: CastSession, p1: Int) {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
            initializeViews(binding)
            initializeCast(binding)
            binding.userIcon.setOnClickListener{
                startActivity(Intent(requireActivity(),UserActivity::class.java))
            }
        }

    }

    private fun initializeCast(binding: FragmentHomeBinding) {
        val btn: MediaRouteButton = binding.cast
        CastButtonFactory.setUpMediaRouteButton(MyApp.instance.applicationContext, btn)
        mCastContext = CastContext.getSharedInstance(requireActivity())
        mCastSession = mCastContext?.sessionManager?.currentCastSession
        mCastStateListener = CastStateListener { newState ->
            if (newState != CastState.NO_DEVICES_AVAILABLE) {
                showIntroductoryOverlay()
            }
        }

    }

    private fun intentToJoin() {
        val intent = Intent()
        val intentToJoinUri = Uri.parse("https://castvideos.com/cast/join")
        if (intent.data != null && intent.data == intentToJoinUri) {
            mCastContext!!.sessionManager.startSession(intent)
        }
    }

    private fun showIntroductoryOverlay() {
        if (mIntroductoryOverlay != null) {
            mIntroductoryOverlay!!.remove()
        }
        mIntroductoryOverlay = IntroductoryOverlay.Builder(requireActivity(), _binding?.cast!!)
            .setTitleText("Introducing Cast")
            .setOverlayColor(R.color.primary)
            .setSingleTime()
            .setOnOverlayDismissedListener { mIntroductoryOverlay = null }
            .build()
        mIntroductoryOverlay!!.show()


    }

    private fun initializeViews(binding: FragmentHomeBinding) {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        posterModel.id = "15"
//        binding.subTitle.text = subtitle.joinToString(" • ")
        posterModel.title = "Work The Out"
        posterModel.poster =
            "https://images.unsplash.com/photo-1580261450046-d0a30080dc9b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fGd5bXxlbnwwfHwwfHw%3D&w=1000&q=80"
        posterModel.mediaUrl = "https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4"
        posterModel.thumbnail =
            "https://images.template.net/wp-content/uploads/2016/10/09141849/MuscleTransform-Fitness-Poster.jpg"
        posterModel.genre = "Adernaline Rush"
        posterModel.description =
            "A gym is a large room, usually containing special equipment, where people go to do physical exercise and get fit. The gym has exercise bikes and running machines. While some guests play golf, others work out in the hotel gym. The large gym offers a variety of exercise equipment and weights going up to 100 pounds."
        setData(posterModel)
        binding.apply {
            movieRecyclerView.apply {
                setLayoutManager(layoutManager)
                moviesAdapter.mList =
                    Preferences.instance.movieList.filter { it.playedDuration == 0L && it.isWishListed == false }
                moviesAdapter.mClickListener = object : ItemClickedListener {
                    override fun onItemClick(view: View, model: AppModel) {
                        super.onItemClick(view, model)
                        startPlayerActivity(model as MoviesModel, true)
                    }
                }
                adapter = moviesAdapter
                playBtn.setOnClickListener {
                    startPlayerActivity(posterModel, true)
                }
                infoBtn.setOnClickListener {
                    startPlayerActivity(posterModel, false)
//                    loadRemoteMedia(0,true)
                }

            }

            categories.setOnClickListener {
                val bm = screenShot(binding.root)
                val dr = BitmapDrawable(bm)
//                MyApp.instance.ssBitmap = dr
//                startActivity(Intent(requireActivity(), CategoriesActivity::class.java))
                boolean = false
                showPopUpAnimation(_binding?.blurView!!)

            }
            movies.setOnClickListener {
                if (isShowing) {
                    hideView(tvShows)
                } else {
                    startCategoriesActivity()
                }

            }
            tvShows.setOnClickListener {

                if (isShowing) {
                    hideView(movies)
                } else {
                    startCategoriesActivity()

                }
            }
            binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                Log.d(
                    "New Y: $scrollY",
                    "OLd Y:  $oldScrollY"
                )
                val layoutParams =
                    (binding.secondaryView.layoutParams as? ViewGroup.MarginLayoutParams)
                layoutParams?.setMargins(0, -(scrollY.toDp()), 0, 0)
                binding.secondaryView.layoutParams = layoutParams
            }
            binding.myList.setOnClickListener {
                isWishListed = !isWishListed
                posterModel.isWishListed = isWishListed

            }
            val layoutManager2 = LinearLayoutManager(requireContext())
            layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
            binding.continueRecycler.layoutManager = layoutManager2
            continueAdapter.mClickListener = object : ItemClickedListener {
                override fun onItemClick(view: View, model: AppModel) {
                    startPlayerActivity(model as MoviesModel, true)
                }
            }
            continueAdapter.mInfoClickListener = object : ItemClickedListener {
                override fun onItemClick(view: View, model: AppModel) {
                    startPlayerActivity(model as MoviesModel, false)
                }
            }
            continueAdapter.mMenuClickListener = object : ItemClickedListener {
                override fun onItemClick(view: View, model: AppModel) {
                    showList(model as MoviesModel)
                }
            }
            continueAdapter.mList =
                Preferences.instance.movieList.filter { it.playedDuration != 0L && it.isWishListed == false }
            binding.continueRecycler.adapter = continueAdapter

            val lm = LinearLayoutManager(requireContext())
            lm.orientation = LinearLayoutManager.HORIZONTAL
            wishListAdapter.apply {

                binding.wishlistRecycler.layoutManager = lm
                mClickListener = object : ItemClickedListener {
                    override fun onItemClick(view: View, model: AppModel) {
                        super.onItemClick(view, model)
                        startPlayerActivity(model as MoviesModel, true)
                    }
                }
                binding.wishlistRecycler.adapter = wishListAdapter
            }
        }
    }

    private fun setData(model: MoviesModel) {
        _binding?.apply {
            Glide.with(requireContext()).load(model.poster).into(moviePoster)
            genre.text = model.genre
            if (model.isWishListed == true) {
                isWishListed = model.isWishListed!!
            } else {
                isWishListed = false
            }

        }
    }

    private fun startPlayerActivity(moviesModel: MoviesModel, shouldPlay: Boolean) {
        val intent = Intent(requireActivity(), PlayerActivity::class.java)
        intent.putExtra(Constants.EXTRA_MOVIE_MODEL, moviesModel)
        intent.putExtra(Constants.SHOULD_PLAY, shouldPlay)
        startActivity(intent)


    }

    var boolean = false
    private fun startCategoriesActivity() {
        val intent = Intent(requireActivity(), CategoriesActivity::class.java)
        val bm = screenShot(_binding?.root!!)
        val dr = BitmapDrawable(bm)
        MyApp.instance.ssBitmap = dr
        intent.putExtra(Constants.NAV, true)
//        categoriesResultLauncher.launch(intent)
        boolean = true
        showPopUpAnimation(_binding?.blurView!!)
    }

    private fun showView(view: View) {

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.enter_from_left)
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

                view.alpha = 0.5f
                view.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                view.alpha = 1f
                view.visibility = View.VISIBLE
            }
        })
        view.startAnimation(animation)
    }


    private fun hideView(view: View) {
        isShowing = false
        _binding?.navSection?.setPadding(0, 0, 0, 0)
        val p = _binding?.navSection?.layoutParams
        p?.width = LinearLayout.LayoutParams.WRAP_CONTENT
        _binding?.tvCategories?.textSize = 12f
//        val dp = 16
//        val px = dp.toPx()
//        val layoutParams = LinearLayout.LayoutParams(px, px)

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_left)
        //use this to make it longer:  animation.setDuration(1000);
        var count = 0
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                object : CountDownTimer(animation.duration, animation.duration / 10) {
                    override fun onFinish() {
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        count++
                        val alpha = (10f - count) / 10
                        view.alpha = alpha
                    }
                }.start()

            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                view.visibility = View.GONE
            }
        })
        view.startAnimation(animation)
    }

    var categoriesResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val nav = data?.getIntExtra(Constants.NAV, 0)
                val binding = _binding
                if (binding != null) {
                    when (nav) {
                        NAV.HOME -> {
                            isShowing = true
                            binding.navSection.setPadding(30, 0, 30, 0)
                            showView(binding.movies)
                            showView(binding.tvShows)
                            val p = _binding?.navSection?.layoutParams
                            p?.width = LinearLayout.LayoutParams.MATCH_PARENT
                            _binding?.tvCategories?.textSize = 16f
//                            val dp = 24
//                            val px = dp.toPx()
//                            val layoutParams = LinearLayout.LayoutParams(px, px)
                        }
                        NAV.MOVIES -> {
                            showView(binding.movies)
                            hideView(binding.tvShows)
                        }
                        NAV.TVSHOWS -> {
                            hideView(binding.movies)
                            showView(binding.tvShows)
                        }
                    }

                }
            }
        }

    private fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun showList(model: MoviesModel) {
        val sheet = OptionsSheet().build(requireContext()) {
            title(model.title)
            displayMode(DisplayMode.LIST)
            with(
                Option(R.drawable.ic_info, "Info"),
                Option(R.drawable.ic_download, "Download"),
                Option(R.drawable.ic_dislike, "Not for me"),
                Option(R.drawable.ic_like, "I like this"),
                Option(R.drawable.ic_loved_it, "Love this!"),
                Option(R.drawable.close, "Remove from row")

            )
            onPositive { index: Int, _: Option ->
                when (index) {
                    0 -> {

                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    4 -> {

                    }
                    5 -> {

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

    override fun onResume() {
        refreshData()
        super.onResume()

        mCastContext!!.addCastStateListener(mCastStateListener!!)
        mCastContext!!.sessionManager.addSessionManagerListener(
            mSessionManagerListener, CastSession::class.java
        )
        intentToJoin()
        if (mCastSession == null) {
            mCastSession = CastContext.getSharedInstance(requireContext()).sessionManager
                .currentCastSession
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mCastContext!!.removeCastStateListener(mCastStateListener!!)
        mCastContext!!.sessionManager.removeSessionManagerListener(
            mSessionManagerListener, CastSession::class.java
        )
    }

    private fun loadRemoteMedia(position: Int, autoPlay: Boolean) {
        if (mCastSession == null) {
            return
        }
        val remoteMediaClient = mCastSession!!.remoteMediaClient ?: return
        remoteMediaClient.registerCallback(object : RemoteMediaClient.Callback() {
            override fun onStatusUpdated() {
                val intent = Intent(requireActivity(), ExpandedControlsActivity::class.java)
                startActivity(intent)
                remoteMediaClient.unregisterCallback(this)
            }
        })
        remoteMediaClient.load(
            MediaLoadRequestData.Builder()
                .setMediaInfo(posterModel.mediaInfo)
                .setAutoplay(autoPlay)
                .setCurrentTime(position.toLong()).build()
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData() {
        wishListAdapter.mList = Preferences.instance.movieList.filter { it.isWishListed == true }
        continueAdapter.mList =
            Preferences.instance.movieList.filter { it.playedDuration != 0L && it.isWishListed == false }
        moviesAdapter.mList =
            Preferences.instance.movieList.filter { it.playedDuration == 0L && it.isWishListed == false }
        wishListAdapter.notifyDataSetChanged()
        continueAdapter.notifyDataSetChanged()
        moviesAdapter.notifyDataSetChanged()

    }

    var mList: List<String> = arrayListOf(
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy",
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy",
        "Marvel",
        "DC",
        "Action",
        "Animation",
        "Sci-Fi",
        "Romantic",
        "Documentry",
        "Horror",
        "Comedy",
        "Horror-Comedy"
    )
    private val navList = arrayListOf("Home", "TV Shows", "Movies")
    val categoriesAdapter = CategoriesAdapter()
    fun showPopUp() {
        _binding?.blurView?.visibility = View.VISIBLE
        val lm = LinearLayoutManager(requireContext())
        val dataList: ArrayList<CategoriesModel> = arrayListOf()

        if (boolean) {
            for (i in navList) {
                val model = CategoriesModel()
                model.title = i
                dataList.add(model)
            }
        } else {
            for (i in mList) {
                val model = CategoriesModel()
                model.title = i
                dataList.add(model)
            }
        }
        _binding?.apply {
            closeBtn.setOnClickListener {
                hidePopUpAnimation(_binding?.blurView!!)
            }
            categoriesRecycler.apply {
                if (boolean) {
                    categoriesAdapter.isNav = boolean
                }
                layoutManager = lm
                categoriesAdapter.mList = dataList
                adapter = categoriesAdapter
                categoriesAdapter.notifyDataSetChanged()
            }
        }
        categoriesAdapter.mNavListener = object : ItemClickedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(view: View, pos: Int) {
                for (i in categoriesAdapter.mList) {
                    i.isSelected = false
                }
                categoriesAdapter.mList[pos].isSelected = true
                categoriesAdapter.notifyDataSetChanged()
                if (categoriesAdapter.isNav) {
                    val binding = _binding
                    if (binding != null) {
                        when (pos) {
                            NAV.HOME -> {
                                isShowing = true
                                binding.navSection.setPadding(30, 0, 30, 0)
                                showView(binding.movies)
                                showView(binding.tvShows)
                                val p = _binding?.navSection?.layoutParams
                                p?.width = LinearLayout.LayoutParams.MATCH_PARENT
                                _binding?.tvCategories?.textSize = 16f
//                            val dp = 24
//                            val px = dp.toPx()
//                            val layoutParams = LinearLayout.LayoutParams(px, px)
                            }
                            NAV.MOVIES -> {
                                showView(binding.movies)
                                hideView(binding.tvShows)
                            }
                            NAV.TVSHOWS -> {
                                hideView(binding.movies)
                                showView(binding.tvShows)
                            }
                        }
                        hidePopUpAnimation(binding.blurView)
                    }
                }
            }

        }
    }


    fun showPopUpAnimation(view: View) {
        _binding?.blurView?.startBlur()
        _binding?.blurView?.lockView()
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.enter_from_bottom)
        //use this to make it longer:  animation.setDuration(1000);
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                val mainActivity = requireActivity() as MainActivity
                mainActivity.hide()
                view.visibility = View.VISIBLE

            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                showPopUp()
            }
        })
        view.startAnimation(animation)
    }
    fun hidePopUpAnimation(view: View){
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_bottom)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
                Log.d("Repeat", "")
            }

            override fun onAnimationEnd(animation: Animation) {
                val mainActivity = requireActivity() as MainActivity
                mainActivity.show()
              view.visibility = View.GONE
            }
        })
        view.startAnimation(animation)
    }
}
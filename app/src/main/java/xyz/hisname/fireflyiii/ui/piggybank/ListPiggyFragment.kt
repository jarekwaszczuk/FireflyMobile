package xyz.hisname.fireflyiii.ui.piggybank

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.base_swipe_layout.*
import kotlinx.android.synthetic.main.fragment_base_list.*
import xyz.hisname.fireflyiii.R
import xyz.hisname.fireflyiii.repository.models.piggy.PiggyData
import xyz.hisname.fireflyiii.ui.base.BaseFragment
import xyz.hisname.fireflyiii.util.extension.create
import xyz.hisname.fireflyiii.util.extension.display
import xyz.hisname.fireflyiii.util.extension.hideFab
import xyz.hisname.fireflyiii.util.extension.toastError

class ListPiggyFragment: BaseFragment() {

    private var dataAdapter = arrayListOf<PiggyData>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.create(R.layout.fragment_base_list, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        runLayoutAnimation(recycler_view)
        displayView()
        pullToRefresh()
        initFab()
    }

    private fun displayView(){
        swipeContainer.isRefreshing = true
        piggyViewModel.getAllPiggyBanks().observe(this, Observer { piggyBankData ->
            piggyViewModel.isLoading.observe(this, Observer { loading ->
                if (loading == false) {
                    swipeContainer.isRefreshing = false
                    if (piggyBankData.isNotEmpty()) {
                        dataAdapter = ArrayList(piggyBankData)
                                listText.isVisible = false
                        listImage.isVisible = false
                        recycler_view.isVisible = true
                        recycler_view.adapter =  PiggyRecyclerAdapter(dataAdapter){ data: PiggyData -> itemClicked(data) }.apply {
                            update(dataAdapter)
                        }
                    } else {
                        listText.text = resources.getString(R.string.no_piggy_bank)
                        listText.isVisible = true
                        listImage.isVisible = true
                        listImage.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_piggy_bank))
                        recycler_view.isVisible = false
                    }
                }
            })
        })

        piggyViewModel.apiResponse.observe(this, Observer {
            toastError(it)
        })
    }

    private fun itemClicked(piggyData: PiggyData){
        val bundle = bundleOf("piggyId" to piggyData.piggyId)
        requireFragmentManager().commit {
            replace(R.id.fragment_container, PiggyDetailFragment().apply { arguments = bundle })
            addToBackStack(null)
        }
    }

    private fun pullToRefresh(){
        swipeContainer.setOnRefreshListener {
            dataAdapter.clear()
            displayView()
        }
    }

    private fun initFab(){
        fab.display {
            fab.isClickable = false
            requireFragmentManager().commit {
                replace(R.id.bigger_fragment_container, AddPiggyFragment().apply {
                    arguments = bundleOf("revealX" to fab.width / 2, "revealY" to fab.height / 2)
                })
                addToBackStack(null)
            }
            fab.isClickable = true
        }
        recycler_view.hideFab(fab)
    }

    override fun onAttach(context: Context){
        super.onAttach(context)
        activity?.activity_toolbar?.title = resources.getString(R.string.piggy_bank)
    }

    override fun onResume() {
        super.onResume()
        activity?.activity_toolbar?.title = resources.getString(R.string.piggy_bank)
    }

    override fun onDetach() {
        super.onDetach()
        fab.isGone = true
    }

    override fun handleBack() {
        requireFragmentManager().popBackStack()
    }
}
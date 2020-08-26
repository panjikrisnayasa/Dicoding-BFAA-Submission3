package com.panjikrisnayasa.submission3.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panjikrisnayasa.submission3.R
import com.panjikrisnayasa.submission3.adapter.UserSearchAdapter
import com.panjikrisnayasa.submission3.viewmodel.FollowingListViewModel
import kotlinx.android.synthetic.main.fragment_following_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowingListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var mViewModel: FollowingListViewModel
    private lateinit var mUserSearchAdapter: UserSearchAdapter

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        recycler_following_list.setHasFixedSize(true)
        recycler_following_list.layoutManager = LinearLayoutManager(context)
        showRecyclerFollowing()

        mViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingListViewModel::class.java
        )

        val extraUsername = arguments?.getString(DetailActivity.EXTRA_USERNAME)
        if (extraUsername != null) {
            mViewModel.setFollowing(extraUsername)
            mViewModel.getFollowing()
                .observe(this.viewLifecycleOwner, Observer { following ->
                    showLoading(false)
                    if (following != null) {
                        mUserSearchAdapter.setUserSearchData(following)
                    } else
                        text_following_list_no_data.visibility = View.VISIBLE
                })
        }
    }

    private fun showRecyclerFollowing() {
        mUserSearchAdapter = UserSearchAdapter()
        mUserSearchAdapter.notifyDataSetChanged()
        recycler_following_list.adapter = mUserSearchAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state)
            progress_following_list_loading.visibility = View.VISIBLE
        else
            progress_following_list_loading.visibility = View.GONE
    }
}
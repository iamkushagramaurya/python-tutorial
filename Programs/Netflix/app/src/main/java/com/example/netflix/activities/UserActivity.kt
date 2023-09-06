package com.example.netflix.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.children
import com.example.netflix.Constants
import com.example.netflix.R
import com.example.netflix.databinding.ActivityUserBinding
import com.example.netflix.interfaces.ItemClickedListener
import com.example.netflix.layouts.AddItemLayout
import com.example.netflix.layouts.UserItemLayout
import com.example.netflix.models.AppModel
import com.example.netflix.models.UserModel
import com.google.android.flexbox.FlexboxLayout

class UserActivity : ParentActivity() {

    private lateinit var binding: ActivityUserBinding
    var selectedIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var isEditable = false
        val addLayout = AddItemLayout(this)
        binding.flexBox.addView(addLayout, 0)
        addLayout.setOnClickListener {
            val children = binding.flexBox.childCount
            if (children < 6) {
                addView()
            }
            binding.flexBox.setOnHierarchyChangeListener(object :
                ViewGroup.OnHierarchyChangeListener {
                override fun onChildViewAdded(parent: View?, i: View?) {
                    if (binding.flexBox.childCount >= 6) {
                        binding.flexBox.removeView(addLayout)
                    }
                    if (i is UserItemLayout) {
                        i.mClickListener = object : ItemClickedListener {

                            override fun onItemClick(view: View, model: AppModel) {
                                val p = parent as FlexboxLayout
                                selectedIndex = p.indexOfChild(i)
                                val userModel = model as UserModel
                                val intent =
                                    Intent(this@UserActivity, EditProfileActivity::class.java)
                                intent.putExtra(Constants.EXTRA_USER_MODEL, userModel)
                                editActivityResult.launch(intent)
                            }
                        }
                        i.setOnClickListener {
                            startActivity(Intent(this@UserActivity, MainActivity::class.java))
                        }
                    }
                }

                override fun onChildViewRemoved(parent: View?, child: View?) {
                    if (binding.flexBox.childCount < 6) {
                        binding.flexBox.addView(addLayout)
                    }

                }
            })
        }
        binding.editBtn.setOnClickListener {
            isEditable = !isEditable
            for (i in binding.flexBox.children) {
                if (i is UserItemLayout) {
                    val model = i.userModel
                    model?.isEditable = isEditable
                    i.userModel = model
                }
            }
            if (isEditable) {
                binding.editBtn.text = getString(R.string.done)
            } else {
                binding.editBtn.text = getString(R.string.edit)
            }
        }

    }

    private fun addView() {
        val intent = Intent(this, AddUserActivity::class.java)
        addUserActivityResult.launch(intent)
    }


    private val addUserActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val model = data?.getSerializableExtra(Constants.EXTRA_USER_MODEL) as UserModel
                val layout = UserItemLayout(this)
                layout.userModel = model
                binding.flexBox.addView(layout, 0)
            }

        }
    private val editActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val model = data?.getSerializableExtra(Constants.EXTRA_USER_MODEL) as UserModel
                val layout = binding.flexBox.getChildAt(selectedIndex) as UserItemLayout
                layout.userModel=model
            }

        }
}



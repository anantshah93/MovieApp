package iostudio.`in`.topmovies.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import iostudio.`in`.topmovies.R
import iostudio.`in`.topmovies.utils.dismissLLoadingDialog
import iostudio.`in`.topmovies.utils.showDialog
import iostudio.`in`.topmovies.utils.showLoadingDialog
import iostudio.`in`.topmovies.viewmodel.BaseViewModel
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {
    protected abstract val viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeErrorEvent()
    }

    private fun observeErrorEvent() {
        viewModel.apply {
            isLoading.observe(this@BaseActivity) {
                handleLoading(it == true)
            }
            errorMessage.observe(this@BaseActivity) {
                handleErrorMessage(it)
            }
            noInternetConnectionEvent.observe(this@BaseActivity) {
                handleErrorMessage(getString(R.string.no_internet_connectivity))
            }
            connectTimeoutEvent.observe(this@BaseActivity) {
                handleErrorMessage(getString(R.string.connection_timeout))
            }
            forceUpdateAppEvent.observe(this@BaseActivity) {
                handleErrorMessage(getString(R.string.force_update_app))
            }
            serverMaintainEvent.observe(this@BaseActivity) {
                handleErrorMessage(getString(R.string.server_maintenance_message))
            }
            unknownErrorEvent.observe(this@BaseActivity) {
                handleErrorMessage(getString(R.string.unknown_error))
            }
        }
    }

    /**
     * override this if not use loading dialog (example progress bar)
     */
    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLLoadingDialog()
        showDialog(
            message = message,
            textPositive = getString(R.string.ok)
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun getFormattedDate(sourceDate: String): String = try {
        val calendar: Calendar = Calendar.getInstance()

        val sourceFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sourceFormat.parse(sourceDate)?.let { calendar.time = it };

        val targetFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        targetFormat.format(calendar.time)
    } catch (e: Exception) {
        e.printStackTrace()
        sourceDate
    }


    fun updateActionBar(isShowBackNavigation: Boolean, title: String) {
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(isShowBackNavigation)
            it.title = title
        }
    }
}
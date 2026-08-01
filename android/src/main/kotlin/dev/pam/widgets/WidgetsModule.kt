package dev.pam.widgets
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import dev.pam.nativeapp.modules.*
import dev.pam.nativeapp.protocol.*
class WidgetsModule(private val context:Context):NativeModule{override fun invoke(method:String,payload:ByteArray,completion:ModuleCompletion){if(method!="update"){completion.complete(ModuleResultStatus.FAILURE,"Unknown method: $method".toByteArray());return};runCatching{val v=WireMap.decode(payload);context.getSharedPreferences("pam.widgets",Context.MODE_PRIVATE).edit().putString("title",v.text("title")).putString("subtitle",v.text("subtitle")).putString("value",v.text("value")).putString("deepLink",v.text("deepLink")).putLong("updatedAtMillis",v.integer("updatedAtMillis")).apply();PamWidgetProvider.refresh(context,AppWidgetManager.getInstance(context),AppWidgetManager.getInstance(context).getAppWidgetIds(ComponentName(context,PamWidgetProvider::class.java)))}.onSuccess{completion.complete(ModuleResultStatus.SUCCESS,WireMap.encode(emptyMap()))}.onFailure{completion.complete(ModuleResultStatus.FAILURE,it.message.orEmpty().toByteArray())}};private fun Map<String,WireValue>.text(k:String)=(get(k)as?WireValue.Text)?.value.orEmpty();private fun Map<String,WireValue>.integer(k:String)=(get(k)as?WireValue.Integer)?.value?:0}

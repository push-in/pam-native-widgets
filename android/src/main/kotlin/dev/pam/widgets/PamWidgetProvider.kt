package dev.pam.widgets
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
class PamWidgetProvider:AppWidgetProvider(){override fun onUpdate(context:Context,manager:AppWidgetManager,ids:IntArray)=refresh(context,manager,ids);companion object{fun refresh(context:Context,manager:AppWidgetManager,ids:IntArray){val p=context.getSharedPreferences("pam.widgets",Context.MODE_PRIVATE);for(id in ids){val views=RemoteViews(context.packageName,dev.pam.widgets.R.layout.pam_widget);views.setTextViewText(dev.pam.widgets.R.id.pam_widget_title,p.getString("title","")?:"");views.setTextViewText(dev.pam.widgets.R.id.pam_widget_subtitle,p.getString("subtitle","")?:"");views.setTextViewText(dev.pam.widgets.R.id.pam_widget_value,p.getString("value","")?:"");val link=p.getString("deepLink","").orEmpty();if(link.isNotEmpty()){val intent=Intent(Intent.ACTION_VIEW,Uri.parse(link)).setPackage(context.packageName);views.setOnClickPendingIntent(dev.pam.widgets.R.id.pam_widget_title,PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))};manager.updateAppWidget(id,views)}}}}

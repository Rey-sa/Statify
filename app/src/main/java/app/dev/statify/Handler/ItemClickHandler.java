//package app.dev.statify.Handler;
//
//import android.content.Intent;
//import app.dev.statify.Activities.ListViewActivity;
//import app.dev.statify.Activities.ResultActivity;
//
//import java.util.ArrayList;
//
//public class ItemClickHandler {
//
//
//
//    public void handleItemClick(int position) {
//
//        ArrayList<Double> selectedData = mArrayList.get(position);
//
//        Intent intent = new Intent(ListViewActivity.this, ResultActivity.class);
//        intent.putExtra("selected_data", selectedData);
//        startActivity(intent);
//        mAdapter.notifyDataSetChanged();
//
//    }
//}

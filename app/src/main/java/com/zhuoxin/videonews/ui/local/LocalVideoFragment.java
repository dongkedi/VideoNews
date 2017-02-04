package com.zhuoxin.videonews.ui.local;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.zhuoxin.videonews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/12/21.
 */

public class LocalVideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.gridView)
    GridView gridView;
    private Unbinder unbinder;
    private LocalVideoAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化当前页面的Loader(加载器，去loader视频数据)
        getLoaderManager().initLoader(0, null, this);
        //初始化适配器
        adapter = new LocalVideoAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        //设置适配器
        gridView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //关闭加载中的线程池
        adapter.release();
    }

    //------------------------------------ start ------------------------------------
    //创建所需loader对象
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MediaStore.Video.Media._ID,//视频Id
                MediaStore.Video.Media.DATA,//视频路径
                MediaStore.Video.Media.DISPLAY_NAME,//视频名称
        };

        return new CursorLoader(
                getContext(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,//视频Uri
                projection,
                null, null, null
        );
    }

    //当数据加载完成后触发
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //设置数据
        adapter.swapCursor(data);
        /*Log.d("LocalVideoFragment", "onLoadfinished" + data.getCount());
        if (data.moveToFirst()) {
            do {
                int index = data.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
                String displayname = data.getString(index);
                Log.d("LocalVideoFragment", displayname);
            } while (data.moveToNext());
        }*/
    }

    //数据加载重置,比如数据清空
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
    //------------------------------------ end ------------------------------------
}

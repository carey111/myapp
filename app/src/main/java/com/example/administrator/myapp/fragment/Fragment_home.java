package com.example.administrator.myapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.myapp.HaoyouActivity;
import com.example.administrator.myapp.PersonDongtaiActivity;
import com.example.administrator.myapp.PictureActivity;
import com.example.administrator.myapp.R;
import com.example.administrator.myapp.YingyueActivity;
import com.example.administrator.myapp.pojo.Dianzan;
import com.example.administrator.myapp.pojo.Dongtai;
import com.example.administrator.myapp.pojo.Remark;
import com.example.administrator.myapp.pojo.User;
import com.example.administrator.myapp.util.HttpUtils;
import com.example.administrator.myapp.util.RefreshListView;
import com.example.administrator.myapp.util.xUtilsImageUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fragment_home extends Fragment implements RefreshListView.OnRefreshUploadChangeListener{

    private static final String TAG = "DongtaiActivity";
    private BaseAdapter adapter;
    private RefreshListView  lv_dtt;
    private List<Dongtai>
            newdongtailist = new ArrayList<Dongtai>();
    private List<Dongtai> dongtaiList = new ArrayList<Dongtai>();
    private RollPagerView mRollViewPager;
    private View view;
    private Button bt_yinyue;

    private Button bt_haoyou;
    private List<String>
            zanuserNamelist = new ArrayList<String>();


    boolean flag = true;
    String resultPage = "";
    String dianzanusername=null;
    Integer dianzanuserId=-1;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int pageNo = 1;
    int pageSize = 3;
    Handler handler = new Handler();
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_home, null);
        lv_dtt = ((RefreshListView ) view.findViewById(R.id.lv_dtt));
//        lv_dtt.setMode(PullToRefreshBase.Mode.BOTH);
        bt_yinyue = ((Button) view.findViewById(R.id.bt_yinyue));


        initEvent();

        getDianZanUserByuserId();
        getDongtailist();
//        PictureRoll();

        return view;

    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void initEvent() {
        bt_yinyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), YingyueActivity.class);
                startActivity(intent);
            }
        });
        bt_haoyou = ((Button) view.findViewById(R.id.bt_haoyou));
        bt_haoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HaoyouActivity.class);
                startActivity(intent);
            }
        });
        lv_dtt.setOnRefreshUploadChangeListener(this);
   }
    private void getDongtailist() {
        RequestParams params = new RequestParams(HttpUtils.url + "GetDongtai");
        params.addQueryStringParameter("pageNo", pageNo + "");
        params.addQueryStringParameter("pageSize", pageSize + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("===" + result);
                Type type = new TypeToken<List<Dongtai>>() {
                }.getType();
                dongtaiList = gson.fromJson(result, type);
                if (flag) {
                    newdongtailist.clear();// 清空原来数据
                } else {
                    if (dongtaiList.size() == 0) {//服务器没有返回新的数据
                        pageNo--; //下一次继续加载这一页
                        Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                        lv_dtt.completeLoad();//没获取到数据也要改变界面
                        return;
                    }
                }
                for (Dongtai dongtai : dongtaiList) {
                    System.out.println(dongtai.getContent() + "==" + dongtai.getUser().getUserId());
                }
                newdongtailist.addAll(dongtaiList);
                if (adapter == null) {
                    adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return newdongtailist.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return null;
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {

                            ViewHolder viewHolder = null;
                            if (convertView == null) {
                                viewHolder = new ViewHolder();
                                convertView = View.inflate(getActivity(), R.layout.layout_item, null);
                                viewHolder.iv_photo1 = ((ImageView) convertView.findViewById(R.id.iv_photo1));
                                viewHolder.tv_title = ((TextView) convertView.findViewById(R.id.tv_title));
                                viewHolder.tv_name = ((TextView) convertView.findViewById(R.id.tv_name));
                                viewHolder.tv_content = ((TextView) convertView.findViewById(R.id.tv_content));
                                viewHolder.tv_remark = ((TextView) convertView.findViewById(R.id.tv_remark));
                                viewHolder.iv_dianzan = ((ImageView) convertView.findViewById(R.id.iv_dianzan));
                                viewHolder.tv_zan = ((TextView) convertView.findViewById(R.id.tv_zan));
                                viewHolder.iv_Image = ((ImageView) convertView.findViewById(R.id.iv_Image));
                                viewHolder.tv_time = ((TextView) convertView.findViewById(R.id.tv_time));
                                convertView.setTag(viewHolder);//缓存对象
                            } else {
                                viewHolder = (ViewHolder) convertView.getTag();
                            }
                            final Dongtai dongtai = newdongtailist.get(position);
                            viewHolder.iv_photo1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    System.out.println("xxx" + dongtai.getUser().getUserId());
                                    Intent intent = new Intent(Fragment_home.this.getActivity(), PersonDongtaiActivity.class);
                                    intent.putExtra("userId", dongtai.getUser().getUserId());
                                    startActivity(intent);
                                }
                            });

                            viewHolder.tv_title.setText(dongtai.getTitle());
                            viewHolder.tv_name.setText(dongtai.getUser().getUserName());
                            viewHolder.tv_content.setText(dongtai.getContent());
                            viewHolder.tv_time.setText(String.valueOf(dongtai.publishtime) );
                            xUtilsImageUtils.display(viewHolder.iv_photo1, HttpUtils.url + dongtai.getUser().getPhotoImg(),true);
                            x.image().bind(viewHolder.iv_Image, HttpUtils.url + dongtai.dongtaiImage);
                            Log.i("jjjjjjjjjjjjj",HttpUtils.url + dongtai.dongtaiImage);
                            final TextView tvZan = viewHolder.tv_zan;


                            if (dongtai.dianzanlist.size() > 0 && dongtai.dianzanlist != null) {
                                System.out.println("dianzanlist.size():" + dongtai.dianzanlist.size());
                                String zan = "";
                                tvZan.setVisibility(View.VISIBLE);
//                                SpannableString ss=new SpannableString("");
                                for (Dianzan dianzan : dongtai.dianzanlist) {
                                    dianzanusername=dianzan.user.getUserName();
                                    dianzanuserId=dianzan.user.getUserId();
                                    zan += dianzanusername+",";
                                    zanuserNamelist.add(dianzanusername);
                                }

                                zan=zan.substring(0,zan.length()-1);
                                zan="我"+zan;
                                SpannableString ss = new SpannableString(zan);
                                Drawable d = getResources().getDrawable(R.drawable.dianzan_blue);
                                d.setBounds(0, 0, 50, 50);
                                ss.setSpan(new ImageSpan(d), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                                int start=1;
//                                int end=start+dianzanusername.length();
//                                ss.setSpan(new Clickable(clickListener),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                for(String zanuserName:zanuserNamelist){
                                zan(start,ss,zanuserName);
                                }
                                tvZan.setMovementMethod(LinkMovementMethod.getInstance());

                                tvZan.setText(ss);
                            } else {
                                tvZan.setVisibility(View.GONE);
                            }
                            //图片点击事件
                            final ImageView image = viewHolder.iv_Image;
                            final String str=HttpUtils.url1 + dongtai.dongtaiImage;
                            viewHolder.iv_Image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), PictureActivity.class);
                                    intent.putExtra("picture",str);
                                    startActivity(intent);
                                }
                            });
                            //点赞设置点击事件
                            final ImageView dz = viewHolder.iv_dianzan;
                            viewHolder.iv_dianzan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (sharedPreferences.contains(position + "")) {//选中状态
                                        dz.setImageResource(R.drawable.dianzan_gray);
//
                                        deleteDianzan((Integer) dz.getTag());
                                        Log.i("Fragment", (Integer) dz.getTag() + "");
                                        Toast.makeText(getActivity(), "取消点赞", Toast.LENGTH_SHORT).show();
                                        editor.remove(position + "");

                                        Timestamp dianzanTime = new Timestamp(System.currentTimeMillis());
                                        Dianzan dianzan = new Dianzan(dongtai.dongtaiId, dianzanTime, user);
                                        dongtai.dianzanlist.remove(dianzan);
                                        tvZan.setText("");
                                        adapter.notifyDataSetChanged();

                                    } else {
                                        dz.setImageResource(R.drawable.dianzan_blue);
                                        insertDianzan((Integer) dz.getTag());
                                        Log.i("Fragment11", (Integer) dz.getTag() + "");
                                        Toast.makeText(getActivity(), "点赞成功", Toast.LENGTH_SHORT).show();
                                        editor.putInt(position + "", (Integer) dz.getTag());
                                        tvZan.setText("雨墨");
                                        Timestamp dianzanTime = new Timestamp(System.currentTimeMillis());
                                        Dianzan dian = new Dianzan(dongtai.dongtaiId, dianzanTime, user);
                                        dongtai.dianzanlist.add(dian);
                                        adapter.notifyDataSetChanged();
                                    }
                                    editor.commit();

                                }
                            });
                            viewHolder.iv_dianzan.setTag(dongtai.dongtaiId);
                            if (sharedPreferences.contains(position + "")) {
                                dz.setImageResource(R.drawable.dianzan_blue);
                            } else {
                                dz.setImageResource(R.drawable.dianzan_gray);
                            }

                            if (null != dongtai.remarklist && dongtai.remarklist.size() > 0) {
                                Log.i("Fragment_home", dongtai.remarklist + "===============");
//                    System.out.print(dongtai.remarklist);
                                viewHolder.tv_remark.setVisibility(View.VISIBLE);
                                String string = "";
                                for (Remark remark : dongtai.remarklist) {
                                    System.out.print(remark);
                                    if ((remark.isEnd) == true) {
                                        string += remark.user.getUserName() + ":" + remark.remarkContent + "\n";
                                    } else  {
                                        string += remark.user.getUserName() + "回复" + remark.fatheruser.getUserName() + ":" + remark.remarkContent + "\n";
                                    }
                                    viewHolder.tv_remark.setText(string);
                                }

                            } else {
                                viewHolder.tv_remark.setVisibility(View.GONE);
                            }


                            return convertView;
                        }
                    };
                    lv_dtt.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                if (!flag){
                    lv_dtt.completeLoad();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println(ex + "");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }

    private void getDianZanUserByuserId() {
        RequestParams params = new RequestParams(HttpUtils.url + "GetDianzanUser");
        params.addBodyParameter("uesrId", 1 + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("=11111111111111111==" + result);
                Type type = new TypeToken<User>() {
                }.getType();
                User user1 = gson.fromJson(result, type);
                user = user1;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=11111111111111111==" + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private static class ViewHolder {
        TextView tv_title;
        TextView tv_name;
        ImageView iv_photo1;
        TextView tv_content;
        TextView tv_remark;
        ImageView iv_dianzan;
        TextView tv_zan;
        ImageView iv_Image;
        TextView tv_time;
    }


    private void deleteDianzan(Integer dongtaiId) {

        RequestParams requestParams = new RequestParams(HttpUtils.url + "deletedianzan");
        requestParams.addQueryStringParameter("userId", 1 + "");
        requestParams.addQueryStringParameter("dongtaiId", dongtaiId + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void insertDianzan(Integer dongtaiId) {

        RequestParams requestParams = new RequestParams(HttpUtils.url + "insertdianzan");
        requestParams.addQueryStringParameter("userId", 1 + "");
        requestParams.addQueryStringParameter("dongtaiId", dongtaiId + "");
        long dianzanTime = System.currentTimeMillis();
        requestParams.addQueryStringParameter("dianzanTime", dianzanTime + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

//    public void PictureRoll() {
//        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
//
//        //设置播放时间间隔
//        mRollViewPager.setPlayDelay(3000);
//        //设置透明度
//        mRollViewPager.setAnimationDurtion(500);
//        //设置适配器
//        mRollViewPager.setAdapter(new TestNormalAdapter());
//
//        //设置指示器（顺序依次）
//        //自定义指示器图片
//        //设置圆点指示器颜色
//        //设置文字指示器
//        //隐藏指示器
//        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
//        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.YELLOW, Color.WHITE));
//        //mRollViewPager.setHintView(new TextHintView(this));
//        //mRollViewPager.setHintView(null);
//
//
//    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private int[] imgs = {
                R.drawable.aaa,
                R.drawable.tiankong2,
                R.drawable.tiankong3,
                R.drawable.tiankong4,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }



//    private  void dealPattern( SpannableString spannableString, Pattern patten, int start) throws Exception {
//        Matcher matcher = patten.matcher(spannableString);
//        while (matcher.find()) {
//            String key = matcher.group();
//            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
//            if (matcher.start() < start) {
//                continue;
//            }
//            // 计算该内容的长度，也就是要替换的字符串的长度
//            int end = matcher.start() + key.length();
//            //设置前景色span
//            spannableString.setSpan(new Clickable(clickListener), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            if (end < spannableString.length()) {
//                // 如果整个字符串还未验证完，则继续。。
//                dealPattern( spannableString, patten, end);
//            }
//            break;
//        }
//    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View widget) {
            Toast.makeText(getActivity(),dianzanuserId+"",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), PersonDongtaiActivity.class);
            intent.putExtra("userId",dianzanuserId);

            startActivity(intent);
        }
    };

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        @Override
        public void onClick(View widget) {
            mListener.onClick(widget);
        }
    }

    private void zan(int start,SpannableString ss,String dianzanusername){
        int end=start+dianzanusername.length();
        ss.setSpan(new Clickable(clickListener),start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        start=end+1;
       if(end<ss.length()){
              start=end+1;
         zan(start,ss,dianzanusername);
      }

    }
    //    上拉刷新，下拉加载
    @Override
    public void onRefresh() {
        pageNo = 1; //每次刷新，让pageNo变成初始值1
        //1秒后发一个消息
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = true;
                getDongtailist(); //再次获取数据
                lv_dtt.completeRefresh();  //刷新数据后，改变页面为初始页面：隐藏头部
            }
        }, 1000);
    }

    @Override
    public void onPull() {
        pageNo++;
        //原来数据基础上增加
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = false;
                getDongtailist();
            }
        }, 1000);
    }



}
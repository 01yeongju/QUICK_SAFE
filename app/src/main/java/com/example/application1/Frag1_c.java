package com.example.application1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Frag1_c extends Fragment {
    private View view;
    private ListView Lv1, Lv2;

    // '게시글' 리스트뷰 변수 선언
    String[] titles = {
            "title1", "title2", "title3", "title4", "title5",
    };
    String[] contents = {
            "와", "죽겠어요", "목이", "아프요", "살려주라요",
    };
    String[] days = {
            "2023.09.01", "2023.09.02", "2023.09.03", "2023.09.04", "2023.09.05",
    };
    Integer[] images = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
    };

    // '실종자' 리스트뷰 변수 선언
    String[] f_titles = {
            "f_title1", "f_title2", "f_title3", "f_title4", "f_title5",
    };
    String[] f_names = {
            "가", "나", "다", "라", "마",
    };
    String[] f_days = {
            "2023.09.06", "2023.09.07", "2023.09.08", "2023.09.09", "2023.09.10",
    };
    Integer[] f_images = {
            R.drawable.img5, R.drawable.img4, R.drawable.img3, R.drawable.img2, R.drawable.img1,
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1_community, container, false);

        Lv1 = view.findViewById(R.id.view1);
        Lv2 = view.findViewById(R.id.view2);

        Lv1.setVisibility(View.INVISIBLE);
        Lv2.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find your "게시물" button and set its click listener here
        View post_Button = view.findViewById(R.id.post);
        View find_person_Button = view.findViewById(R.id.find_person);

        post_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomList1 adapter1 = new CustomList1(getActivity());
                Lv1.setAdapter(adapter1);

                Lv1.setVisibility(View.VISIBLE);
                Lv2.setVisibility(View.INVISIBLE);
            }
        });

        find_person_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomList2 adapter1 = new CustomList2(getActivity());
                Lv2.setAdapter(adapter1);

                Lv1.setVisibility(View.INVISIBLE);
                Lv2.setVisibility(View.VISIBLE);
            }
        });
    }

    // '게시물' 버튼에 대한 리스트뷰
    public class CustomList1 extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList1(Activity context) {
            super(context, R.layout.post_item, titles);
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.post_item, null, true);
            TextView title = (TextView) rowView.findViewById(R.id.titles);
            TextView content = (TextView) rowView.findViewById(R.id.contents);
            TextView day = (TextView) rowView.findViewById(R.id.days);
            ImageView image = (ImageView) rowView.findViewById(R.id.images);

            title.setText(titles[position]);
            content.setText(contents[position]);
            day.setText(days[position]);
            image.setImageResource(images[position]);

            return rowView;
        }
    }

    // '실종자 찾기' 버튼에 대한 리스트뷰
    public class CustomList2 extends ArrayAdapter<String> {
        private final Activity context;
        public CustomList2(Activity context) {
            super(context, R.layout.find_person_item, f_titles);
            this.context = context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.find_person_item, null, true);
            TextView title = (TextView) rowView.findViewById(R.id.find_person_titles);
            TextView name = (TextView) rowView.findViewById(R.id.find_person_names);
            TextView day = (TextView) rowView.findViewById(R.id.find_person_days);
            ImageView image = (ImageView) rowView.findViewById(R.id.find_person_images);

            title.setText(f_titles[position]);
            name.setText(f_names[position]);
            day.setText(f_days[position]);
            image.setImageResource(f_images[position]);

            return rowView;
        }
    }
}

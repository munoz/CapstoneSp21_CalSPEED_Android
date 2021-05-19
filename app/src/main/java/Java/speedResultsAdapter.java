package Java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.cp_kotlin.R;

public final class speedResultsAdapter extends Adapter {
    private final List<speedResults> resultsList;
    private final speedResultsAdapter.OnClickListener onClickListener;

    public speedResultsAdapter.ResultAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.result_row, parent, false);
        return new speedResultsAdapter.ResultAdapterHolder(view);
    }

    public void onBindViewHolder(speedResultsAdapter.ResultAdapterHolder holder, int position) {
        speedResults results = this.resultsList.get(position);
        holder.bind(results, this.onClickListener);
    }

    public void onBindViewHolder(ViewHolder var1, int var2) {
        this.onBindViewHolder((speedResultsAdapter.ResultAdapterHolder)var1, var2);
    }

    public int getItemCount() {
        return this.resultsList.size();
    }

    public speedResultsAdapter(@Nullable List resultsList, speedResultsAdapter.OnClickListener onClickListener) {
        super();
        this.resultsList = resultsList;
        this.onClickListener = onClickListener;
    }

    public static final class ResultAdapterHolder extends ViewHolder {
        private final TextView title;
        private final TextView download;
        private final TextView upload;
        private final TextView latency;

        public final void bind(final speedResults results, final speedResultsAdapter.OnClickListener onClickListener) {
            this.title.setText(results.getDate().toString());
            this.download.setText(Double.toString(results.getDown()));
            this.upload.setText(Double.toString(results.getUp()));
            this.latency.setText(results.getPing());
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.itemView.setOnClickListener((android.view.View.OnClickListener)(new android.view.View.OnClickListener() {
                public final void onClick(View it) {
                    onClickListener.onClick(results);
                }
            }));
        }

        public ResultAdapterHolder(View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.title = itemView.findViewById(R.id.resultName);
            this.download = itemView.findViewById(R.id.Download);
            this.upload = itemView.findViewById(R.id.Upload);
            this.latency = itemView.findViewById(R.id.Ping);
        }
    }

    public static final class OnClickListener {
        private final Function1 clickListener;

        public final void onClick(@NotNull speedResults results) {
            Intrinsics.checkNotNullParameter(results, "results");
            this.clickListener.invoke(results);
        }

        public final Function1 getClickListener() {
            return this.clickListener;
        }

        public OnClickListener(Function1 clickListener) {
            super();
            Intrinsics.checkNotNullParameter(clickListener, "clickListener");
            this.clickListener = clickListener;
        }
    }
}

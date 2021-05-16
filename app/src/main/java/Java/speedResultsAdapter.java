package Java;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import com.cp_kotlin.R;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        xi = 2,
        d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0013\u0014B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"},
        d2 = {"Lcom/cp_kotlin/RecyclerView/ResultAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/cp_kotlin/RecyclerView/ResultAdapter$ResultAdapterHolder;", "resultsList", "", "Lcom/cp_kotlin/RecyclerView/Result;", "onClickListener", "Lcom/cp_kotlin/RecyclerView/ResultAdapter$OnClickListener;", "(Ljava/util/List;Lcom/cp_kotlin/RecyclerView/ResultAdapter$OnClickListener;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "OnClickListener", "ResultAdapterHolder", "app_debug"}
)
public final class speedResultsAdapter extends Adapter {
    private final List<speedResults> resultsList;
    private final speedResultsAdapter.OnClickListener onClickListener;

    @NotNull
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

    public speedResultsAdapter(List<speedResults> resultsList, @NotNull speedResultsAdapter.OnClickListener onClickListener) {
        super();
        this.resultsList = resultsList;
        this.onClickListener = onClickListener;
    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            xi = 2,
            d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
            d2 = {"Lcom/cp_kotlin/RecyclerView/ResultAdapter$ResultAdapterHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "title", "Landroid/widget/TextView;", "bind", "", "results", "Lcom/cp_kotlin/RecyclerView/Result;", "onClickListener", "Lcom/cp_kotlin/RecyclerView/ResultAdapter$OnClickListener;", "app_debug"}
    )
    public static final class ResultAdapterHolder extends ViewHolder {
        private final TextView title;

        public final void bind(final speedResults results, final speedResultsAdapter.OnClickListener onClickListener) {
            this.title.setText(results.getPing());
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.itemView.setOnClickListener((android.view.View.OnClickListener)(new android.view.View.OnClickListener() {
                public final void onClick(View it) {
                    onClickListener.onClick(results);
                }
            }));
        }

        public ResultAdapterHolder(@NotNull View itemView) {
            super(itemView);
            Intrinsics.checkNotNullParameter(itemView, "itemView");
            this.title = itemView.findViewById(R.id.resultName);
        }
    }

    @Metadata(
            mv = {1, 1, 16},
            bv = {1, 0, 3},
            k = 1,
            xi = 2,
            d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u000e\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0004R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000b"},
            d2 = {"Lcom/cp_kotlin/RecyclerView/ResultAdapter$OnClickListener;", "", "clickListener", "Lkotlin/Function1;", "Lcom/cp_kotlin/RecyclerView/Result;", "", "(Lkotlin/jvm/functions/Function1;)V", "getClickListener", "()Lkotlin/jvm/functions/Function1;", "onClick", "results", "app_debug"}
    )
    public static final class OnClickListener {
        @NotNull
        private final Function1 clickListener;

        public final void onClick(@NotNull speedResults results) {
            Intrinsics.checkNotNullParameter(results, "results");
            this.clickListener.invoke(results);
        }

        @NotNull
        public final Function1 getClickListener() {
            return this.clickListener;
        }

        public OnClickListener(@NotNull Function1 clickListener) {
            super();
            Intrinsics.checkNotNullParameter(clickListener, "clickListener");
            this.clickListener = clickListener;
        }
    }
}

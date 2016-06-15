package ca.lalalala.tictactoe;


import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private GridView gridView;
	private BaseAdapter adapter;
	private int[][] moves = new int[3][3];
	private int moveCount = 0;
	public static final int HUMAN = 1;
	public static final int COMPUTER = -1;
	public static final int BLANK = 0;
	public static final int OTHER = 2;
	
	private int humanWins = 0;
	private int computerWins = 0;
	private int ties = 0;
	private int games = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		
		gridView = (GridView) findViewById(R.id.gridview);
		reset();
		adapter = new BaseAdapter() {
			
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2) {
				ImageView iv = (ImageView) getLayoutInflater().inflate(R.layout.button, null);
				switch (moves[arg0 / 3][arg0 % 3]) {
				case COMPUTER:
					iv.setImageResource(R.drawable.computer);
					break;
				case BLANK:
					iv.setImageResource(R.drawable.blank);
					break;
				case HUMAN:
					iv.setImageResource(R.drawable.human);
					break;
				default:
					break;
				}
				return iv;
			}
			
			@Override
			public long getItemId(int arg0) {
				return arg0;
			}
			
			@Override
			public Object getItem(int arg0) {
				return arg0;
			}
			
			@Override
			public int getCount() {
				return 9;
			}
		};
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				User only can click the blank area
				if(moves[arg2 / 3][arg2 % 3] == BLANK){
					moves[arg2 / 3][arg2 % 3] = HUMAN;
					moveCount++;
					adapter.notifyDataSetChanged();
					CheckIsDone cid = isDone();
//				If the game is over, create the dialog window
					if(cid.getisIsdone()){
						createDialog(cid);
					}else{
						computerTurn();
					}
				}
			}
			
		});
	}
	
	/**AI part: computer think 
	 *
	 */
	private void computerTurn(){
		PositionAndValue best = bestMove4Computer();
		int i = best.getX();
		int j = best.getY();
		moves[i][j] = COMPUTER;
		moveCount++;
		adapter.notifyDataSetChanged();
		CheckIsDone cid = isDone();
		if(cid.getisIsdone()){
			createDialog(cid);
		}
	}
	
	/**When Game over, create the dialog window
	 * @param cid
	 */
	private void createDialog(final CheckIsDone cid){
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
		.setTitle(cid.getText())
		.setCancelable(false)
		.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				games++;
				switch (cid.getTextCode()) {
				case -1:
					computerWins++;
					break;
				case 0:
					ties++;
					break;
				case 1:
					humanWins++;
					break;

				default:
					break;
				}
				reset();
//				Random a value as Computer's first move
				if(games % 2 == 1){
					Random rd = new Random();
					int index = rd.nextInt(8);
					moves[index / 3][index % 3] = COMPUTER;
					moveCount++;
				}
				adapter.notifyDataSetChanged();
			}
		});
		builder.show();
	}
	
	/**Set the array to all BLANK, and define the contents of the two TextView 
	 * 
	 */
	private void reset(){
		moveCount = 0;
		for(int i = 0; i < moves.length; i++){
			for(int j = 0; j < moves[0].length; j++){
				moves[i][j] = BLANK;
			}
		}
		TextView tv = (TextView) findViewById(R.id.gamecount);
		tv.setText("You Wins:" + humanWins + "\n" + "Computer Wins:" + computerWins + "\n"
				+ "Ties:" + ties
				);
		TextView tvHint = (TextView)findViewById(R.id.gamehint);
		if(games % 2 == 0){
			tvHint.setText("You First");
		}else{
			tvHint.setText("Computer First");
		}
	}
	
	/**Check if the game is over
	 * @return
	 */
	private CheckIsDone isDone(){
		CheckIsDone cid = new CheckIsDone();
		cid.setIsdone(true);
		for(int i = 0; i < moves.length; i++){
			int c = moves[i][0];
			if(moves[i][1] == c && moves[i][2] == c && c != 0){
				cid.setTextCode(c);
				return cid;
			}
		}
		for(int j = 0; j < moves[0].length; j++){
			int c = moves[0][j];
			if(moves[1][j] == c && moves[2][j] == c && c != 0){
				cid.setTextCode(c);
				return cid;
			}
		}
		if(moves[1][1] != 0){
			if(moves[0][0] == moves[1][1] && moves[2][2] == moves[1][1]){
				cid.setTextCode(moves[1][1]);
				return cid;
			}else if(moves[0][2] == moves[1][1] && moves[2][0] == moves[1][1]){
				cid.setTextCode(moves[1][1]);
				return cid;
			}
		}
		if(moveCount > 8 ){
			cid.setTextCode(BLANK);
			return cid;
		}
		
		return new CheckIsDone(false, OTHER);
	}
	
	/**Find the best move for computer
	 * @return
	 */
	private PositionAndValue bestMove4Computer(){
		int besti = -1;  
	    int bestj = -1;  
	    int bestv = 0;  
	    for(int i = 0; i < 3; i ++){  
	        for(int j = 0;j < 3;j ++){  
	            if(moves[i][j] == BLANK){  
	                moves[i][j] = COMPUTER;  
	                moveCount ++;  
	                CheckIsDone cid = isDone();
//	                when computer win
	                if(cid.getTextCode() == COMPUTER ){  
	                	moveCount--;  
	                    moves[i][j] = BLANK;     
	                    return new PositionAndValue(i, j, 1);  
//	                when tied
	                }else if(cid.getTextCode() == BLANK){  
	                	moveCount--;  
	                    moves[i][j] = BLANK;   
	                    return new PositionAndValue(i, j, 0);  
	                }else{  
	                    int v = bestMove4Human().getValue();  
	                    moveCount--;  
	                    moves[i][j] = BLANK;  
	                    if(besti == -1|| v >= bestv){  
	                        besti = i;  
	                        bestj = j;  
	                        bestv = v;  
	                    }  
	                }
	            }  
	        }  
	    }  
	    return new PositionAndValue(besti, bestj, bestv);  
	}
	
	/**Find the best move for human
	 * @return
	 */
	private PositionAndValue bestMove4Human(){  
	    int besti = -1;  
	    int bestj = -1;  
	    int bestv = 0;  
	    for(int i = 0; i < 3; i++){  
	        for(int j = 0; j < 3; j++){  
	            if(moves[i][j] == BLANK){  
	                moves[i][j] = HUMAN;  
	                moveCount++;  
	                CheckIsDone cid = isDone();
//	                when human win
	                if(cid.getTextCode() == HUMAN){  
	                    moveCount--;  
	                    moves[i][j] = BLANK;     
	                    return new PositionAndValue(i, j, -1);
//	                when tied 
	                }else if(cid.getTextCode() == BLANK){  
	                    moveCount--;  
	                    moves[i][j] = BLANK;   
	                    return new PositionAndValue(i, j, 0);  
	                }else{  
	                    int v = bestMove4Computer().getValue();  
	                    moveCount--;  
	                    moves[i][j] = BLANK;  
	                    if(besti == -1 || v <= bestv){  
	                        besti = i;  
	                        bestj = j;  
	                        bestv = v;  
	                    }  
	                }  
	                  
	            }  
	        }  
	    }  
	    return new PositionAndValue(besti, bestj, bestv);  
	}  
}

package system.map.map;

import system.actor.BaseActor;
import system.map.BaseMap;
import system.map.MapChangeDetail;

public class TestMap extends BaseMap{

	public TestMap(){
		super("TestMap", 0);
	}
	
	@Override
	public void loadMapDetails(){
		BaseActor clark = new BaseActor(1, "Clark");
		clark.setCharacterPose(1);
		clark.setTilePostition(2, 5);
		addNPC(clark);
	}

	@Override
	public void addTeleportPoints() {
		teleport.add(new MapChangeDetail(15, 24, BaseActor.FACE_DOWN,"TestMap2", 15, 1, BaseActor.FACE_DOWN, MapChangeDetail.TILE));
	}

	@Override
	public void loadMapDialogs() {
		//DialogDetail dd;
		//dd = new DialogDetail();
		//dd.addDialog(new MainHero(), 0);
		//dd.addDialog(this.getNPC(0), 0);
		//dd.addDialog(new MainHero(), 1);
		//mapDialog.add(dd);
	}
	

}

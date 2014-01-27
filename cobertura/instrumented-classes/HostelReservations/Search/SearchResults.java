package HostelReservations.Search;

import HostelReservations.RoomsBeds.RoomBeds;

public class SearchResults {
	
	private int search_id;
	private RoomBeds rd;
	
	public SearchResults(int search_id, RoomBeds rd)
	{
		this.search_id = search_id;
		this.rd = rd;
	}
	
	public int getSearchID()
	{
		return search_id;
	}
	
	public RoomBeds getRoomBeds()
	{
		return rd;
	}
}

package pianoman.futurecraft.block;

/**
 * Used to specify in/out settings on machines, not sure if we'll
 * need it anymore.
 * 
 * @author Joseph
 *
 */
public enum EnumMachineSetting 
{
	energyInput(1),
	energyOutput(2);
	
	private int id;
	
	private EnumMachineSetting(int id)
	{
		this.id = id;
	}
	
	public int toInt()
	{
		return this.id;
	}
	
	public static EnumMachineSetting toMachineSetting(int id)
	{
		switch (id)
		{
			case 1: return energyInput;
			case 2: return energyOutput;
			default: return energyInput;
		}
	}
	
}

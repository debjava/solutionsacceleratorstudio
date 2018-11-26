
package com.rrs.corona.solutionsacceleratorstudio.dataobject;

import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * This is class is used to collect all the tree items for transfer
 * 
 * @author Maharajan
 * @see com.rrs.corona.solutionsacceleratorstudio.dataobject.DataObjectView
 */
public class DataObjectTransfer
{
	public void collectAllSubTrees( )
	{
		IStructuredSelection select = ( IStructuredSelection ) DataObjectView.viewer_s
				.getSelection( );
		TreeParent parent = ( TreeParent ) select.getFirstElement( );
		String value = parent.getValue( );

		if( value.equals( DataObjectView.class_s ) )
		{

		}
		else if( value.equals( DataObjectView.field_s ) )
		{
			TreeObject obj[] = parent.getChildren( );
			String[] fieldNames = new String[obj.length];
			String className = parent.getName( );
			String groupName = parent.getParent( ).getName( );
			for( int i = 0; i < obj.length; i++ )
			{
				String name = obj[i].getName( );
				String type = obj[i].getType( );
				fieldNames[i] = type + " " + name;
			}
		}

	}
}

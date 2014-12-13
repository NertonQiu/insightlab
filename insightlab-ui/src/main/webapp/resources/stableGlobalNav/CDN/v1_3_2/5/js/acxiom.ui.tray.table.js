var oTablePET;
var aDataSetPET = [
	 ['checkbox', 'Hello World','65.000','41.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'ended', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'ended', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'paused', '12:25:00'],
	 ['checkbox', 'Hello World','65.000','41.000', 'paused', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Hello World','65.000','41.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00'],
	 ['checkbox', 'Go World','65.000','42.000', 'active', '12:25:00']

];
var optionsPET = {
	"aaData": aDataSetPET,

	"aoColumnDefs": [ 
	  {
			"mRender": function ( data, type, row ) {
				return '<label class="acxm-checkbox"><input type="checkbox"><span></span></label>';
			},
			"aTargets": [ 0 ]
		},
	  {"aTargets": [ 1 ],"sClass":"inline_editable"},
	  {"aTargets": [ 2 ],"sClass":"inline_editable"},
	  {"aTargets": [ 3 ],"sClass":"inline_editable"},
	  
	  {
			"mRender": function ( data, type, row ) {
				if( data == 'active' ){
					 return  '<span class="status-active">active</span>';
				}else if( data == 'paused' ){
					 return '<span class="status-paused">paused</span>'; 
				} else {
					return  '<span class="status-ended">ended</span>';
				} 
			},
			"aTargets": [ 4 ],"sClass":"inline_editable"
	  },
	  
	  
	  {"aTargets": [ 5 ],"sClass":"inline_editable"}
	 ],
	
	"bScrollInfinite": true,
	"bScrollCollapse": true,
	"sScrollY": "250px",
	"bPaginate": false,
	"aaSorting": [[1,'asc']],

	"bFilter": false	
};


function restoreRowPET ( oTablePET, nRow )
{
	var aData = oTablePET.fnGetData(nRow);
	var jqTds = $('>td', nRow);
	jqTds.parents('tr').removeClass("selected_tr");	
	for ( var i=0, iLen=jqTds.length ; i<iLen ; i++ ) {
		oTablePET.fnUpdate( aData[i], nRow, i, false );
	}			
	oTablePET.fnDraw();
}
function editRowPET ( oTablePET, nRow )
{	
    var aData = oTablePET.fnGetData(nRow);
    var jqTds = $('>td', nRow);
	jqTds.parents('tr').addClass("selected_tr");
    jqTds[1].innerHTML = '<input class="acxiom-input" type="text" value="'+aData[1]+'"><span class="notes_link">NOTES</span><div class="note"><div class="note-row"><label>Details</label><a href="#">LinkProfile</a><a href="#">LinkData</a><a href="#">LinkReport</a></div><div class="note-row"><label>Notes</label><textarea></textarea></div><div class="note-row"><input type="button" class="acxiom-button primary" value="SAVE"><input type="button" class="acxiom-button clear" value="Cancel" id="notesCancel"></div>';
	jqTds[2].innerHTML = '<div class="acxiom-select"><select><option>'+aData[2]+'</option></select></div>';
    jqTds[3].innerHTML = '<div class="acxiom-select"><select><option>'+aData[3]+'</option></select></div>';
    jqTds[4].innerHTML = '<div class="acxiom-select"><select><option seleced="seleced">active</option><option>ended</option><option>paused</option></select></div>';
    jqTds[5].innerHTML = '<input class="acxiom-input" type="text" value="'+aData[5]+'">';



} 

function saveRowPET ( oTablePET, nRow )
{
    var jqInputs = $('input', nRow);
    oTablePET.fnUpdate( jqInputs[0].value, nRow, 0, false );
    oTablePET.fnDraw();
}

function init_example(elem_id){

	oTablePET = $("#"+elem_id).dataTable(optionsPET);
	
	//add inline edit
	var nEditing = null;

    $('#example').on('click', 'td.inline_editable', function (e) {
		
    	//$(this).unbind("click");
        e.preventDefault();
         
        /* Get the row as a parent of the link that was clicked on */
        var nRow = $(this).parents('tr')[0];
		        
        //todo 
        if( nEditing == nRow ){
        	return;
        }
        
        if ( nEditing !== null && nEditing != nRow ) {
            /* A different row is being edited - the edit should be cancelled and this row edited */
            restoreRowPET( oTablePET, nEditing );
            editRowPET( oTablePET, nRow );
            nEditing = nRow;
        }
        else if ( nEditing == nRow ) {
            /* This row is being edited and should be saved */
            saveRowPET( oTablePET, nEditing );
            nEditing = null;
        }
        else {
            /* No row currently being edited */
            editRowPET( oTablePET, nRow );
            nEditing = nRow;
        }
		$('.notes_link').click(function () {
		$(this).parents('td').addClass("oppen-row");
		
        });
		$('#notesCancel').click(function () {
		$(this).parents('td').removeClass("oppen-row");	
        });
		
		$('thead #check_all').click(function () {
				$('#example tr').find(':checkbox').attr('checked', this.checked);
    	});
		
    } );
	

}



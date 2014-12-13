$(function()
{
	$('table.sortable tbody tr:odd').addClass('odd');
	$('table.sortable tbody tr:even').addClass('even');

	var alternateRowColors = function($table)
	{
		$('tbody tr:odd', $table).removeClass('even').addClass('odd');
		$('tbody tr:even', $table).removeClass('odd').addClass('even');
	};
	
	$('table.sortable').each(function()
	{
		var $table = $(this);
		alternateRowColors($table);
		$('th', $table).each(function(column)
		{
			if ($(this).is('.alphabetic'))
			{
				$(this).hover(function()
				{
				}, function(){
				}).click(function()
				{
				$(this).toggleClass('sorted');
					var sortOrder = $('table.sortable').data('order');
					var rows = $table.find('tbody > tr').get();

					if(sortOrder == 'd' || sortOrder == '')
					{
						$('table.sortable').data('order','a');
						rows.sort(function(a, b)
						{
							var keyA = $(a).children('td').eq(column).text().toUpperCase();
							var keyB = $(b).children('td').eq(column).text().toUpperCase();
	
							if (keyA < keyB)
							{
	//							alert(keyA +'<'+ keyB);
								return 1;
							}
							if (keyA > keyB)
							{
	//							keyA +'>'+ keyB);						
								return -1;
							}
							return 0;
						});
					}
					else
					{
						$('table.sortable').data('order','d');	
						rows.sort(function(a, b)
						{
							var keyA = $(a).children('td').eq(column).text().toUpperCase();
							var keyB = $(b).children('td').eq(column).text().toUpperCase();
	
							if (keyA < keyB)
							{
	//							alert(keyA +'<'+ keyB);
								return -1;
							}
							if (keyA > keyB)
							{
	//							keyA +'>'+ keyB);						
								return 1;
							}
							return 0;
						});
					}
//					alert($('table.sortable').data('order'));

					$.each(rows, function(index, row)
					{
						$table.children('tbody').append(row);
					});
					alternateRowColors($table);
				});
			}
		});
	});
});

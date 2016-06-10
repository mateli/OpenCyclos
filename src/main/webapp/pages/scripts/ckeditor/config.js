/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	config.skin = 'cyclos';
	config.uiColor = '#f1f1f1';	
	config.toolbar = 'Cyclos';	
	config.toolbar_Cyclos =
	[
	    { name: 'top', items : [ 'Bold','Italic','Underline', 'TextColor', 'JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock',
	                                     '-', 'NumberedList', 'BulletedList', '-','Outdent','Indent','-', 'Link','Unlink', 'HorizontalRule', 'Image', 'SpecialChar' ] },
	    { name: 'bottom', items : [ 'Font','FontSize', 'Source', 'Maximize' ] },
		
	];	
};

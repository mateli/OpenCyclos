function currentTheme() {
	var selected = getValue('themeSelect');
	if (selected == null) return null;
	return themes.find(function(theme) {
		return theme.filename == selected;
	});
}

function updateTheme() {
	var theme = currentTheme();
	if (theme == null) {
		theme = {title:'', author:'', styles:'', version:'', description:''};
	}
	setValue('tdTitle', trim("" + theme.title));
	setValue('tdAuthor', trim("" + theme.author));
	setValue('tdStyles', trim("" + theme.styles));
	setValue('tdVersion', trim("" + theme.version));
	setValue('tdDescription', replaceAll(trim("" + theme.description), '\n', '<br/>'));
}

Behaviour.register({
	'#themeSelect': function(select) {
		select.onchange = updateTheme;
	},
	
	'#selectForm': function(form) {
		form.onsubmit = function() {
			if (currentTheme() == null) {
				alert(nothingSelectedMessage);
				return false;
			}
			return confirm(selectConfirmationMessage);
		}
	},
	
	'#removeButton': function(button) {
		button.onclick = function() {
			if (currentTheme() == null) {
				alert(nothingSelectedMessage);
				return false;
			}
			if (confirm(removeConfirmationMessage)) {
				var form = $('removeForm');
				form.filename.value = getValue('themeSelect');
				form.submit();
			}
		}
	}
});

addOnReadyListener(function() {
	var themeSelect = $('themeSelect');
	setOptions(themeSelect, themes, true, false, 'title', 'filename');
	themeSelect.options[0].text = chooseThemeMessage;
	themeSelect.selectedIndex = 0;
	updateTheme();
});
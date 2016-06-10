Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'.draggableList': function(list) {
		Sortable.create(list);
	}
});
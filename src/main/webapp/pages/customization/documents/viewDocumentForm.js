Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			var win = printResults();
			form.target = win.name
		}
	}
});


Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	}
});

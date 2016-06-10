
function updateLink(a, href) {
	if (!a) return;
	
	var content = trim(a.innerHTML);
	var number = parseInt(content, 10);
	if (number > 0) {
		setPointer(a);
		Element.addClassName(a, "default");
		a.onclick = function() {
			self.location = pathPrefix + href;
		}
	} else {
		a.style.cursor = "text";
		Element.removeClassName(a, "default");
		a.onclick = null;
	}
}

function refreshStatus() {
	new Ajax.Request(pathPrefix + "/getApplicationStatusAjax", {
		method: 'get', 
		onSuccess: function(request, status) {
			if (status == null) {
				return;
			}
			try { $('connectedAdmins').innerHTML = status.connectedAdmins; } catch (e) {}
			try { $('connectedMembers').innerHTML = status.connectedMembers; } catch (e) {}
			try { $('connectedBrokers').innerHTML = status.connectedBrokers; } catch (e) {}
			try { $('connectedOperators').innerHTML = status.connectedOperators; } catch (e) {}
			try { $('memberAlerts').innerHTML = status.memberAlerts; } catch (e) {}
			try { $('systemAlerts').innerHTML = status.systemAlerts; } catch (e) {}
			try { $('errors').innerHTML = status.errors; } catch (e) {}
			try { $('uptime').innerHTML = replaceAll(replaceAll(uptimeMessage, "#days#", status.uptimeDays), "#hours#", status.uptimeHours); } catch (e) {}
			try { $('unreadMessages').innerHTML = status.unreadMessages; } catch (e) {}
			try { $('openInvoices').innerHTML = status.openInvoices; } catch (e) {}
			updateLinks();
		},
		
		onError: function() {
			try {
				clearInterval(intervalId);
			} catch (e) {}
		}
	});
}

function updateLinks() {
	updateLink($('connectedAdmins'), "/listConnectedUsers?nature=ADMIN");
	updateLink($('connectedMembers'), "/listConnectedUsers?nature=MEMBER");
	updateLink($('connectedBrokers'), "/listConnectedUsers?nature=BROKER");
	updateLink($('connectedOperators'), "/listConnectedUsers?nature=OPERATOR");
	updateLink($('systemAlerts'), "/systemAlerts");
	updateLink($('memberAlerts'), "/memberAlerts");
	updateLink($('errors'), "/viewErrorLog");
	updateLink($('unreadMessages'), "/searchMessages");
	updateLink($('openInvoices'), "/searchInvoices");
}

addOnReadyListener(updateLinks);

Behaviour.register({
	'#refreshStatus': function(a) {
		setPointer(a);
		a.onclick = refreshStatus;
	}
});
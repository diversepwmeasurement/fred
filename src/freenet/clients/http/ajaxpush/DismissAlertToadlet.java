package freenet.clients.http.ajaxpush;

import java.io.IOException;
import java.net.URI;

import freenet.client.HighLevelSimpleClient;
import freenet.clients.http.RedirectException;
import freenet.clients.http.SimpleToadletServer;
import freenet.clients.http.Toadlet;
import freenet.clients.http.ToadletContext;
import freenet.clients.http.ToadletContextClosedException;
import freenet.clients.http.updateableelements.UpdaterConstants;
import freenet.support.HTMLDecoder;
import freenet.support.Logger;
import freenet.support.api.HTTPRequest;

public class DismissAlertToadlet extends Toadlet {

	private static volatile boolean	logMINOR;

	static {
		Logger.registerClass(DismissAlertToadlet.class);
	}

	public DismissAlertToadlet(HighLevelSimpleClient client) {
		super(client);
	}

	@Override
	public void handleGet(URI uri, HTTPRequest req, ToadletContext ctx) throws ToadletContextClosedException, IOException, RedirectException {
		String anchor = HTMLDecoder.decode(req.getParam("anchor"));
		if (logMINOR) {
			Logger.minor(this, "Dismissing alert with anchor:" + anchor);
		}
		boolean success = ((SimpleToadletServer) ctx.getContainer()).getCore().alerts.dismissByAnchor(anchor);
		writeHTMLReply(ctx, 200, "OK", success ? UpdaterConstants.SUCCESS : UpdaterConstants.FAILURE);
	}

	@Override
	public String path() {
		return UpdaterConstants.dismissAlertPath;
	}

	@Override
	public String supportedMethods() {
		return "GET";
	}

}
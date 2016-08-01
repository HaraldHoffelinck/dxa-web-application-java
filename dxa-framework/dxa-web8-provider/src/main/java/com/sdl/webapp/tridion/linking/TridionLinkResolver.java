package com.sdl.webapp.tridion.linking;

import com.sdl.webapp.common.api.WebRequestContext;
import com.sdl.webapp.tridion.BrokerComponentPresentationProvider;
import com.tridion.linking.BinaryLink;
import com.tridion.linking.ComponentLink;
import com.tridion.linking.Link;
import com.tridion.linking.PageLink;
import com.tridion.util.TCMURI;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
/**
 * <p>TridionLinkResolver class.</p>
 */
public class TridionLinkResolver extends AbstractTridionLinkResolver {

    private static final Logger LOG = LoggerFactory.getLogger(BrokerComponentPresentationProvider.class);

    @Autowired
    private WebRequestContext webRequestContext;

    private static final LinkStrategy BINARY_LINK_STRATEGY = new LinkStrategy() {
        @Override
        public Link getLink(int publicationId, int itemId, String uri) {
            return new BinaryLink(publicationId)
                    .getLink(uri.startsWith("tcm:") ? uri : ("tcm:" + uri), null, null, null, false);
        }
    };

    private static final LinkStrategy COMPONENT_LINK_STRATEGY = new LinkStrategy() {
        @Override
        public Link getLink(int publicationId, int itemId, String uri) {
            TCMURI Uri = null;
            try {
                Uri = new TCMURI(uri);

            } catch (ParseException e) {
                LOG.warn("Error when parsing Tcm Uri {}", uri);
            }

            if (Uri == null) {
                return new ComponentLink(publicationId).getLink(itemId);
            } else {
                return new ComponentLink(publicationId).getLink(Uri.getItemId(), itemId, 0, "", "link", true, false);
            }
        }
    };

    private static final LinkStrategy PAGE_LINK_STRATEGY = new LinkStrategy() {
        @Override
        public Link getLink(int publicationId, int itemId, String uri) {
            return new PageLink(publicationId).getLink(itemId);
        }
    };

    private static Map<BasicLinkStrategy, LinkStrategy> strategiesMapping;

    static {
        strategiesMapping = new HashMap<>();
        strategiesMapping.put(BasicLinkStrategy.BINARY_LINK_STRATEGY, BINARY_LINK_STRATEGY);
        strategiesMapping.put(BasicLinkStrategy.COMPONENT_LINK_STRATEGY, COMPONENT_LINK_STRATEGY);
        strategiesMapping.put(BasicLinkStrategy.PAGE_LINK_STRATEGY, PAGE_LINK_STRATEGY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String resolveLink(BasicLinkStrategy linkStrategy, int publicationId, int itemId, String uri) {
        if(linkStrategy == BasicLinkStrategy.COMPONENT_LINK_STRATEGY && uri == null && webRequestContext != null && webRequestContext.getPageId() != null) {
                uri = webRequestContext.getPageId();
        }

        return resolveLink(strategiesMapping.get(linkStrategy), publicationId, itemId, uri);
    }

    @Synchronized
    private String resolveLink(LinkStrategy linkStrategy, int publicationId, int itemId, String uri) {
        final Link link = linkStrategy.getLink(publicationId, itemId, uri);
        if(link.getAnchor() != null) {
            return String.format("%s#%s", link.getURL(), link.getAnchor());
        }
        else {
            return link.getURL();
        }
    }

    private interface LinkStrategy {
        Link getLink(int publicationId, int itemId, String uri);
    }
}

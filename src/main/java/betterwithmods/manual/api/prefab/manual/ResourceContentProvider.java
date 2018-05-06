package betterwithmods.manual.api.prefab.manual;

import betterwithmods.BWMod;
import betterwithmods.manual.api.manual.ContentProvider;
import com.google.common.base.Charsets;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Basic implementation of a content provider based on Minecraft's resource
 * loading framework.
 * <p>
 * Beware that the manual is unaware of resource domains. In other words, two
 * paths that are identical except for their resource domain will be the same,
 * as seen from the manual. This means you should probably place your
 * documentation somewhere other than <tt>doc/</tt>, because that's where the
 * RTFM documentation lives, and it is queried first - meaning if you
 * have a page with the same path as one in RTFM, it is practically
 * unreachable (because the OC provider is always queried first).
 */
@SuppressWarnings("UnusedDeclaration")
public class ResourceContentProvider implements ContentProvider {
    private final String resourceDomain;

    private final String basePath;

    public ResourceContentProvider(String resourceDomain, String basePath) {
        this.resourceDomain = resourceDomain;
        this.basePath = basePath;
    }

    public ResourceContentProvider(String resourceDomain) {
        this(resourceDomain, "");
    }

    @Override
    public Iterable<String> getContent(String path) {
        final ResourceLocation location = new ResourceLocation(resourceDomain, basePath + path);
        try (InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream()) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charsets.UTF_8));
            final ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Throwable ignored) {
            return null;
        }
    }

    @Override
    public int compareTo(ContentProvider contentProvider) {
        if (contentProvider instanceof ResourceContentProvider) {
            ResourceContentProvider c = (ResourceContentProvider) contentProvider;
            if (c.resourceDomain.equals(BWMod.MODID)) {
                return -1;
            }
        }
        return 1;
    }
}